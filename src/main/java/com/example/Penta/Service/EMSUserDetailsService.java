package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.dto.RegisterRequest;
import com.example.Penta.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EMSUserDetailsService {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EMSUserRepository emsUserRepository;

    public RegisterResponse saveEMSUser(RegisterRequest registerRequest){
        Optional<EMSUser> emsUser = emsUserRepository.findByEmail(registerRequest.getEmail());
        if(!emsUser.isPresent()){
            var user = EMSUser.builder()
                    .email(registerRequest.getEmail())
                    .name(registerRequest.getName())
                    .phone(registerRequest.getPhone())
                    .status("ACTIVE")
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .build();

            EMSUser savedUser =  emsUserRepository.save(user);
            return new RegisterResponse(savedUser.getUser_id(),"User Created");
        }
        else{
            return new RegisterResponse(null,"User already Exists");
        }

    }

}
