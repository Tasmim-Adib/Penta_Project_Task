package com.example.Penta.Service;

import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.dto.AuthRequest;
import com.example.Penta.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EMSUserRepository repository;

    public AuthResponse authenticate(AuthRequest request) {
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
}
