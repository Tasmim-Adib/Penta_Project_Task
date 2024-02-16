package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    private UUID user_id;
    private String name;
    private String faculty_name;
    private String designation;
    private String email;
    private String phone;
    private String status;
    private int role_id;

}
