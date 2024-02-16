package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllStudentWithAdvisorResponse {
    private UUID user_id;
    private String name;
    private String email;
    private String student_id;
    private String department_name;
    private String phone;
    private int batch_no;
}
