package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Role;

public class EMSUserBuilder {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String status;
    private Role role;

    public EMSUserBuilder setName(String name){
        this.name = name;
        return this;
    }
    public EMSUserBuilder setEmail(String email){
        this.email = email;
        return this;
    }
    public EMSUserBuilder setPassword(String password){
        this.password = password;
        return this;
    }
    public EMSUserBuilder setPhone(String phone){
        this.phone = phone;
        return this;
    }
    public EMSUserBuilder setStatus(String status){
        this.status = status;
        return this;
    }
    public EMSUserBuilder setRole(Role role){
        this.role = role;
        return this;
    }

    public EMSUser getEmsUser(){
        return new EMSUser(null,email,password,name,phone,status,role);
    }
}
