package com.barberFlow.barberFlow.dto;
import com.barberFlow.barberFlow.model.Funcao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(@NotBlank @Size(max = 150) String nome, @Email @NotBlank @Size(max = 150) String email, @NotBlank @Size(min = 6, max = 72) String senha, @NotNull Funcao funcao) {

    public UsuarioRequest {
        email = email == null ? null : email.trim().toLowerCase();
    }
}
