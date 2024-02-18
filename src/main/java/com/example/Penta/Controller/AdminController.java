package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.dto.UpdateUserRole;
import com.example.Penta.dto.UpdateUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EMSUserDetailsService emsUserDetailsService;

    //Set User's Role
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/role/{user_id}")
    public ResponseEntity<?> updateUserRole(@PathVariable("user_id") UUID userId, @RequestBody UpdateUserRole updateUserRole){

        String response = emsUserDetailsService.updateUserRole(userId,updateUserRole.getRole_id());
        if(response.equals("User Role Added")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update User's Status
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/status/{user_id}")
    public ResponseEntity<?> updateUserStatus(@PathVariable("user_id") UUID userId, @RequestBody UpdateUserStatus updateUserStatus){

        String response = emsUserDetailsService.updateUserStatus(userId,updateUserStatus.getUserStatus());
        if(response.equals("User Status Updated")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/user/{user_id}")
    public ResponseEntity<?> getUserByUserId(@PathVariable("user_id") UUID user_id){
        Optional<EMSUser> optionalEMSUser = emsUserDetailsService.findUserByUserId(user_id);
        if(optionalEMSUser.isPresent()){
            EMSUser emsUser = optionalEMSUser.get();
            return new ResponseEntity<>(emsUser,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        }
    }
}
