package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateRequest {
    private String name;
    private String phone;
    private int batch_no;
    private String department_name;
    private String student_id;
}
