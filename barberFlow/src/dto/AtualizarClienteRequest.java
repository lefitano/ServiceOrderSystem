package com.barberflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.barberflow.dto.AtualizarClienteRequest;

public record AtualizarClienteRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String telefone, @Size(min = 6) String senha) {
    
}
