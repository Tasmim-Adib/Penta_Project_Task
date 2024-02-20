package com.example.Penta.Config;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.dto.RegisterRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
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
import java.util.UUID;

@Component
@Data
public class Oauth2LoginSuccessConfig extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private EMSUserDetailsService emsUserDetailsService;
    private int role;
    private UUID user_id;

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
            this.setDefaultTargetUrl("http://localhost:8080/set/get/093c51a0-119e-474d-b666-d978804b9005");
            super.onAuthenticationSuccess(request, response, authentication);
        }
        else if("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){

            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email","").toString();
            String name = attributes.getOrDefault("name","").toString();

            emsUserDetailsService.findByEmail(email)
                    .ifPresentOrElse(emsUser -> {
                        setRole(emsUser.getRole().getRole_id());
                        setUser_id(emsUser.getUser_id());
                        DefaultOAuth2User newUser = new DefaultOAuth2User(
                                List.of(new SimpleGrantedAuthority(emsUser.getRole().getRoleEnum().name()))
                                ,attributes,"email");

                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser,List.of(new SimpleGrantedAuthority("ROLE_" + emsUser.getRole().getRoleEnum().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    },() ->{
                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setEmail(email);
                        registerRequest.setName(name);
                        registerRequest.setPassword("");
                        registerRequest.setPhone("");
                        emsUserDetailsService.saveEMSUser(registerRequest);
                        setRole(0);

                    });
            this.setAlwaysUseDefaultTargetUrl(true);
            if(role == 2){
                this.setDefaultTargetUrl("http://localhost:3000/student/"+this.user_id.toString());
            }
            else if(role == 3){
                this.setDefaultTargetUrl("http://localhost:3000/teacher/"+this.user_id.toString());
            }
            else if(role == 1){
                this.setDefaultTargetUrl("http://localhost:3000/admin");
            }
            else{
                this.setDefaultTargetUrl("http://localhost:3000/wait/until/confirm/role");
            }
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
