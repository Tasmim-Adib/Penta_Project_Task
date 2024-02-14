package com.example.Penta.Service;

import com.example.Penta.Entity.MailKeyMap;
import com.example.Penta.Repository.MailKeyMapRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailKeyService {
    @Autowired
    private MailKeyMapRepository mailKeyMapRepository;

    public String saveMailAndKey(MailKeyMap mailKeyMap){
        mailKeyMapRepository.save(mailKeyMap);
        return "Mail Key Saved";
    }

    public MailKeyMap getMailAndKey(String email){
        Optional<MailKeyMap> optionalMailKeyMap = mailKeyMapRepository.findMailKey(email);
        if(optionalMailKeyMap.isPresent()){
            MailKeyMap mailKey = optionalMailKeyMap.get();
            return mailKey;
        }
        else{
            return null;
        }
    }

    @Transactional
    public String deleteTheMailKey(String email) {
        mailKeyMapRepository.deleteByEmail(email);
        return "Entity Deleted";
    }
}
