package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUpdateRequest {
    private String name;
    private String phone;
    private String faculty_name;
    private String designation;
}
