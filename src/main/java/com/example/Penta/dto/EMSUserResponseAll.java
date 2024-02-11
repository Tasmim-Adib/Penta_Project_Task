package com.example.Penta.dto;

import com.example.Penta.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EMSUserResponseAll {
    private UUID user_id;
    private String email;
    private String name;
    private String phone;
    private String status;
    private Role role;
}
