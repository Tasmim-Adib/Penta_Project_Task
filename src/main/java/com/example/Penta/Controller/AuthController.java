package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.*;
import com.example.Penta.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
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

    @GetMapping("/find/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email")String email){
        Optional<EMSUser> optionalEMSUser = emsUserDetailsService.findByEmail(email);
        EmsUserResponseSingle emsUserResponseSingle = new EmsUserResponseSingle();
        if(optionalEMSUser.isPresent()){
            EMSUser emsUser = optionalEMSUser.get();
            emsUserResponseSingle.setUser_id(emsUser.getUser_id());
            emsUserResponseSingle.setRole_id(emsUser.getRole().getRole_id());
            return new ResponseEntity<>(emsUserResponseSingle,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PutMapping("/reset/password/{email}")
    public ResponseEntity<?> resetPassword(@PathVariable("email") String email, @RequestBody ResetPasswordRequest request){
        String response = emsUserDetailsService.resetPassword(email,request);

        if(response.equals("Password Reset Successfully")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/phone/{user_id}")
    public ResponseEntity<?> updatePhone(@PathVariable("user_id") UUID user_id, @RequestBody UpdatePhoneRequest request){
        String response = emsUserDetailsService.updatePhone(user_id,request);
        if(response.equals("Phone updated")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
