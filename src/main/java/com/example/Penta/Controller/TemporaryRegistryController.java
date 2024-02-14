package com.example.Penta.Controller;

import com.example.Penta.Entity.TemporaryRegistration;
import com.example.Penta.Service.TemporaryRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/temp")
public class TemporaryRegistryController {

    @Autowired
    private TemporaryRegistryService temporaryRegistryService;
    @PostMapping("/save")
    public ResponseEntity<?> saveTemporaryUser(@RequestBody TemporaryRegistration request){
        String response = temporaryRegistryService.saveTemporaryUser(request);
        if(response.equals("Temporary User Saved")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
