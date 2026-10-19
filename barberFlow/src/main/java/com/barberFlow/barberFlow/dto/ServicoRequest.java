package com.barberFlow.barberFlow.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ServicoRequest(@NotBlank @Size(max = 100) String nome, @NotBlank @Size(max = 100) String descricao, @NotNull @Positive Integer duracaoMinutos, @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal preco) {
    
}
