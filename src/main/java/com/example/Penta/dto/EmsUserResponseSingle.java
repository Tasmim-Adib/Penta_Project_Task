package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmsUserResponseSingle {
    private UUID user_id;
    private int role_id;
}
