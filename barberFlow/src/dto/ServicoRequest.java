package com.barberflow.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServicoRequest(@NotBlank String nome, @NotBlank String descricao, @NotNull @Positive  Integer duracaoMinutos, @NotNull @Positive  BigDecimal preco) {
    
}
