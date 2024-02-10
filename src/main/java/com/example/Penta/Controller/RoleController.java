package com.example.Penta.Controller;

import com.example.Penta.Entity.Role;
import com.example.Penta.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping("/role")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        try{
            Role savedRole = roleService.addRole(role);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        }catch (Exception e) {
            String errorMessage = "An error occured";
            return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
