package com.barberFlow.barberFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfissionalRequest(@NotBlank @Size(max = 150) String nome, @NotBlank @Email @Size(max = 150) String email, @NotBlank @Size(min = 6, max = 72) String senha, @NotBlank @Size(max = 150) String especialidade, @NotBlank @Size(max = 150) String bio) {

    public ProfissionalRequest {
        email = email == null ? null : email.trim().toLowerCase();
    }
}
