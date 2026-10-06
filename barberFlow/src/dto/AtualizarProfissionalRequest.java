package com.barberflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarProfissionalRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String especialidade, @NotBlank String bio, @Size(min = 6) String senha) {
    
}