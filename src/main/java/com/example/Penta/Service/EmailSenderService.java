package com.example.Penta.Service;

import com.example.Penta.dto.MailSendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public String sendMail(MailSendRequest request){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("adibskitto@gmail.com");
        message.setTo(request.getReceiver());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());

        mailSender.send(message);
        return "Mail Sent Successfully";
    }
}
