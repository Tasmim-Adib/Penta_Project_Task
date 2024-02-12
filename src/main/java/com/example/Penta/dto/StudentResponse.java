package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private UUID user_id;
    private String name;
    private String department_name;
    private String student_id;
    private int batch_no;
    private String email;
    private String phone;
    private String status;
    private int role_id;
    private String advisor;
}
