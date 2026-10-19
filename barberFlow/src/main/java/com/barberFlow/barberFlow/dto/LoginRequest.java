package com.barberFlow.barberFlow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank @Size(max = 150) String email, @NotBlank @Size(max = 72) String senha) {

    public LoginRequest {
        email = email == null ? null : email.trim().toLowerCase();
    }
}
