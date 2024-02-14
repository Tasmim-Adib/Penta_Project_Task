package com.example.Penta.Service;

import com.example.Penta.Entity.MailKeyMap;
import com.example.Penta.dto.MailSendRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailKeyService mailKeyService;

    @Transactional
    public String sendMail(MailSendRequest request){
        SimpleMailMessage message = new SimpleMailMessage();
        Random random = new Random();
        int value = 10000 + random.nextInt(90000);
        message.setFrom("adibskitto@gmail.com");
        message.setTo(request.getReceiver());
        message.setSubject(request.getSubject());
        message.setText("Your Secret Key : "+value);
        MailKeyMap mailKeyMap = new MailKeyMap();
        mailKeyMap.setEmail(request.getReceiver());
        mailKeyMap.setKey(value);
        String response = mailKeyService.saveMailAndKey(mailKeyMap);
        if(response.equals("Mail Key Saved")){
            mailSender.send(message);
            return "Mail Sent Successfully";
        }
        else{
            return "Mail Can't Send";
        }

    }
}
