package com.example.Penta.Controller;

import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.dto.UpdateUserRole;
import com.example.Penta.dto.UpdateUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AdminController {

    @Autowired
    private EMSUserDetailsService emsUserDetailsService;

    //Set User's Role
    @PutMapping("/update/role/{user_id}")
    public ResponseEntity<?> updateUserRole(@PathVariable("user_id") UUID userId, @RequestBody UpdateUserRole updateUserRole){
        try{
            emsUserDetailsService.updateUserRole(userId, updateUserRole.getRole_id());
            return ResponseEntity.ok("Role Added");
        }
        catch (Exception e){
            return new ResponseEntity<>("Can't be Updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Update User's Status
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
}
