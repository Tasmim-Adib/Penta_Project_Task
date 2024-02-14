package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.*;
import com.example.Penta.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EMSUserDetailsService emsUserDetailsService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private MailKeyService mailKeyService;


    @PostMapping("/authenticate")
    public ResponseEntity<?>register(@RequestBody AuthRequest request){
        try{
            return ResponseEntity.ok(service.authenticate(request));
        }
        catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error: " + e.getMessage());
        }

    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest){

        return emsUserDetailsService.saveEMSUser(registerRequest);
    }

    // find all EMSUser
    @GetMapping("/findall")
    public List<EMSUserResponseAll> findAll(){
        return emsUserDetailsService.findAllUser();
    }

    @PostMapping("/mail")
    public ResponseEntity<?> sendMail(@RequestBody MailSendRequest request){
        String response = emailSenderService.sendMail(request);
        if(response.equals("Mail Sent Successfully")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Mail can't be sent",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PutMapping("/reset/password/{user_id}")
    public ResponseEntity<?> resetPassword(@PathVariable("user_id") UUID user_id, @RequestBody String password){
        String response = emsUserDetailsService.resetPassword(user_id,password);

        if(response.equals("Password Reset Successfully")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
