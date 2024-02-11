package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.AuthService;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.Service.JwtService;
import com.example.Penta.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EMSUserDetailsService emsUserDetailsService;
    @PostMapping("/authenticate")
    public ResponseEntity<?>register(@RequestBody AuthRequest request){

        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest){
        return emsUserDetailsService.saveEMSUser(registerRequest);
    }

    @PutMapping("/update/role/{user_id}")
    public ResponseEntity<?> updateUserRole(@PathVariable("user_id") UUID userId, @RequestBody UpdateUserRole updateUserRole){
        try{
            emsUserDetailsService.updateUserRole(userId, updateUserRole.getRole_id());
            return ResponseEntity.ok("Role Added");
        }
        catch (Exception e){
            return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/status/{user_id}")
    public ResponseEntity<?> updateUserStatus(@PathVariable("user_id") UUID userId, @RequestBody UpdateUserStatus updateUserStatus){
        try{
            emsUserDetailsService.updateUserStatus(userId, updateUserStatus.getUserStatus());
            return ResponseEntity.ok("Status Updated");
        }
        catch (Exception e){
            return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/findall")
    @ResponseBody
    public List<EMSUserResponseAll> findAll(){
        return emsUserDetailsService.findAllUser();
    }
}
