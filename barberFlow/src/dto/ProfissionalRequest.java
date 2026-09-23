package com.barberflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfissionalRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String senha, @NotBlank String especialidade, @NotBlank String bio) {
    
}
