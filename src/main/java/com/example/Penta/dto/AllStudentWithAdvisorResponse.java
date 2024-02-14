package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllStudentWithAdvisorResponse {
    private String name;
    private String email;
    private String student_id;
    private String department_name;
    private String phone;
    private int batch_no;
}
