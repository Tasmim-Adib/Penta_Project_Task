package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Role;

public interface EMSUserFactory {
    EMSUser createEmsUser(String email, String password, String name, String phone, String status, Role role);
}
