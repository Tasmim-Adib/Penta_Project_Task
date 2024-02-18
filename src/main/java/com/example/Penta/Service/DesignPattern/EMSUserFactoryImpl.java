package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EMSUserFactoryImpl implements EMSUserFactory{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public EMSUser createEmsUser(String email, String password, String name, String phone, String status, Role role) {
        EMSUser emsUser = new EMSUserBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setPhone(phone)
                .setRole(role)
                .setStatus(status)
                .getEmsUser();
        return emsUser;
    }
}
