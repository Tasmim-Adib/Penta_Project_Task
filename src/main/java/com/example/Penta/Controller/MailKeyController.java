package com.example.Penta.Controller;

import com.example.Penta.Entity.MailKeyMap;
import com.example.Penta.Service.MailKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class MailKeyController {
    @Autowired
    private MailKeyService mailKeyService;

    @GetMapping("/mail/retrieve/key/{email}")
    public ResponseEntity<?> getMailAndKey(@PathVariable("email") String email){
        MailKeyMap mailKeyMap = mailKeyService.getMailAndKey(email);
        if(mailKeyMap != null){
            return new ResponseEntity<>(mailKeyMap, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Mail Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/mail/delete/{email}")
    public ResponseEntity<?> deleteMail(@PathVariable("email") String email) {
        String response = mailKeyService.deleteTheMailKey(email);
        if(response.equals("Entity Deleted")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
