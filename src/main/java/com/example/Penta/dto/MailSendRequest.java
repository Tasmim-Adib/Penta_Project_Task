package com.example.Penta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSendRequest {
    private String receiver;
    private String subject;
    private String body;
}
