package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllTeacherResponse {
    private UUID user_id;
    private String name;
    private String email;
    private String phone;
    private String faculty_name;
    private String designatoin;
}
