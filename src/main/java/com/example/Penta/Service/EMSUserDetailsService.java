package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Role;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.RoleRepository;

import com.example.Penta.dto.EMSUserResponseAll;
import com.example.Penta.dto.RegisterRequest;
import com.example.Penta.dto.RegisterResponse;
import com.example.Penta.dto.ResetPasswordRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EMSUserDetailsService {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EMSUserRepository emsUserRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TemporaryRegistryService temporaryRegistryService;
    @Autowired
    private MailKeyService mailKeyService;

    @Transactional
    public RegisterResponse saveEMSUser(RegisterRequest registerRequest){
        Optional<EMSUser> emsUser = emsUserRepository.findByEmail(registerRequest.getEmail());
        if(!emsUser.isPresent()){
            var user = EMSUser.builder()
                    .email(registerRequest.getEmail())
                    .name(registerRequest.getName())
                    .phone(registerRequest.getPhone())
                    .status("ACTIVE")
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .build();

            EMSUser savedUser =  emsUserRepository.save(user);
            temporaryRegistryService.deleteFromTemporaryTable(registerRequest.getEmail());
            String r = mailKeyService.deleteTheMailKey(registerRequest.getEmail());
            return new RegisterResponse(savedUser.getUser_id(),"User Created");
        }
        else{
            return new RegisterResponse(null,"User already Exists");
        }

    }

    public Optional<EMSUser> findUserByUserId(UUID user_id){
        return emsUserRepository.findByUserId(user_id);
    }

    public Optional<EMSUser> findByEmail(String email){
        return emsUserRepository.findByEmail(email);
    }
    public String updateUserRole(UUID user_id, int role_id){
        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByUserId(user_id);

        if(!optionalEMSUser.isEmpty()){
            EMSUser user = optionalEMSUser.get();

            Optional<Role> roleEntity = roleRepository.findById(role_id);

            if(roleEntity.isPresent()){
                Role role = roleEntity.get();
                user.setRole(role);
            }
            emsUserRepository.save(user);
            return "User Role Added";
        }
        else{
            throw new EntityNotFoundException("User not found with ID: " + user_id);
        }
    }

    public String updateUserStatus(UUID user_id, String status){
        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByUserId(user_id);

        if(!optionalEMSUser.isEmpty()){
            EMSUser user = optionalEMSUser.get();
            user.setStatus(status);
            emsUserRepository.save(user);
            return "User Status Updated";
        }
        else{
            throw new EntityNotFoundException("User not found with ID: " + user_id);
        }
    }

    //Finding all EMSUser
    public List<EMSUserResponseAll> findAllUser(){
        List<EMSUser> user = emsUserRepository.findAll();
        return user.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private EMSUserResponseAll convertToDTO(EMSUser user){
        EMSUserResponseAll dto = new EMSUserResponseAll();
        dto.setUser_id(user.getUser_id());
        dto.setEmail((user.getEmail()));
        dto.setStatus(user.getStatus());
        dto.setPhone(user.getPhone());
        dto.setName(user.getName());

        Role roleEntity = user.getRole();
        if(roleEntity != null){
            Role role = new Role(roleEntity.getRole_id(),roleEntity.getRoleEnum(),roleEntity.getDescription());
            dto.setRole(role);
        }
        return dto;
    }

    public String resetPassword(String email, ResetPasswordRequest request){

        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByEmail(email);
        if(optionalEMSUser.isPresent()){
            EMSUser emsUser = optionalEMSUser.get();

            emsUser.setPassword(passwordEncoder.encode(request.getPassword()));
            emsUserRepository.save(emsUser);
            return "Password Reset Successfully";
        }
        else{
            return "Password Can't Reset";
        }
    }
//    public EMSUser createUser(UUID user_id){
//        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByUserId(user_id);
//        if(optionalEMSUser.isPresent()){
//            EMSUser user = optionalEMSUser.get();
//            int roleId = user.getRole().getRole_id();
//
//            switch (roleId){
//                case 2:
//                    return studentFactory.createUser();
//                case 3:
//                    return teacherFactory.createUser();
//                default:
//                    throw new IllegalArgumentException("Invalid user type");
//            }
//        }
//        else{
//            throw new IllegalArgumentException("User not found for ID: " + user_id);
//        }
//    }
}
