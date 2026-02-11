package com.example.tickettriage.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank @Size(min = 5, max = 120) String subject,
        @NotBlank @Size(min = 20, max = 4000) String description,
        @NotBlank @Email String customerEmail) {
}
