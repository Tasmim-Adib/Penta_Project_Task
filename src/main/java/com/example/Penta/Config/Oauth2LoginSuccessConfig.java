package com.example.Penta.Config;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.dto.RegisterRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class Oauth2LoginSuccessConfig extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private EMSUserDetailsService emsUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        if("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email","").toString();
            String name = attributes.getOrDefault("name","").toString();

            emsUserDetailsService.findByEmail(email)
                    .ifPresentOrElse(emsUser -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(
                                List.of(new SimpleGrantedAuthority(emsUser.getRole().getRoleEnum().name()))
                                ,attributes,"id");

                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser,List.of(new SimpleGrantedAuthority(emsUser.getRole().getRoleEnum().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    },() ->{
                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setEmail(email);
                        registerRequest.setName(name);
                        registerRequest.setPassword("password");
                        registerRequest.setPhone("01757493031");
                        emsUserDetailsService.saveEMSUser(registerRequest);
                    });
            this.setAlwaysUseDefaultTargetUrl(true);
            this.setDefaultTargetUrl("http://localhost:3000");
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
