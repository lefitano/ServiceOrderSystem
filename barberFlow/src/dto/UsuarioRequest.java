package com.barberflow.dto;
import com.barberflow.model.Funcao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(@NotBlank String nome, @Email @NotBlank String email, @NotBlank String senha, @NotNull Funcao funcao) {
    
}
