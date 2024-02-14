package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.TemporaryRegistration;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.TemporaryRegistryRepository;
import com.example.Penta.dto.MailSendRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemporaryRegistryService {
    @Autowired
    private TemporaryRegistryRepository temporaryRegistryRepository;

    @Autowired
    private EMSUserRepository emsUserRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Transactional
    public String saveTemporaryUser(TemporaryRegistration temporaryRegistration){
        String email = temporaryRegistration.getEmail();
        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByEmail(email);
        if(optionalEMSUser.isPresent()){
            return "User Already Exists";
        }
        Optional<TemporaryRegistration> optional = temporaryRegistryRepository.findByUserMail(email);
        if(optional.isPresent()){
            return "User Already Exists";
        }

        temporaryRegistryRepository.save(temporaryRegistration);
        MailSendRequest request = new MailSendRequest();
        request.setReceiver(email);
        request.setSubject("Confirm Your Account");
        String response = emailSenderService.sendMail(request);
        if(response.equals("Mail Sent Successfully")){
            return "Temporary User Saved";
        }
        else{
            return response;
        }
    }

    @Transactional
    public void deleteFromTemporaryTable(String email){
        temporaryRegistryRepository.deleteFromTemporaryTable(email);
    }

    public TemporaryRegistration findTemporaryUserByEmail(String email){
        Optional<TemporaryRegistration> optional = temporaryRegistryRepository.findByUserMail(email);
        if(optional.isPresent()){
            TemporaryRegistration temp = optional.get();
            return temp;
        }
        else{
            throw new UsernameNotFoundException("User not Found");
        }
    }
}
