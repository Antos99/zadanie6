package com.example.zadanie_6.model.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private EmailAddress from;
    private List<EmailAddress> to;
    private String subject;
    private String text;
}
