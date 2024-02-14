package com.example.Penta.Service;

import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.dto.AuthRequest;
import com.example.Penta.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EMSUserRepository repository;
    private final UserDetailsService userDetailsService;
    public AuthResponse authenticate(AuthRequest request) throws AuthenticationException {
        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            if(userDetails == null){
                throw new UsernameNotFoundException("User doesn't Exists");
            }
            if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
                throw new BadCredentialsException("Password Not matches");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));

            //get user details using user mail
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();

            if(user.getRole() == null){
                return AuthResponse.builder()
                        .error("You have not assigned any role")
                        .build();
            }

            if(user.getStatus().equals("DEACTIVE")){
                return AuthResponse.builder()
                        .error("You are DEACTIVATED")
                        .build();
            }
            //generate token using the details
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        catch (UsernameNotFoundException | BadCredentialsException e){
            throw new AuthenticationException("Invalid UserName or Password" + e) {
            };
        }

    }
}
