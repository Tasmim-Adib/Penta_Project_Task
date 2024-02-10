package com.example.Penta.Service;

import com.example.Penta.Entity.Role;
import com.example.Penta.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Role addRole(Role role){
        return roleRepository.save(role);
    }
}
