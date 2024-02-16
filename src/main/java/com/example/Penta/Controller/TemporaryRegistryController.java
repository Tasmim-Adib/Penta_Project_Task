package com.example.Penta.Controller;

import com.example.Penta.Entity.TemporaryRegistration;
import com.example.Penta.Service.TemporaryRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

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

    //find temporary user by email
    @GetMapping("/get/temporary/user/{email}")
    public ResponseEntity<?> getTemporaryUserByEmail(@PathVariable("email") String email){
        TemporaryRegistration temporaryRegistration = temporaryRegistryService.findTemporaryUserByEmail(email);
        return new ResponseEntity<>(temporaryRegistration,HttpStatus.OK);
    }
}
