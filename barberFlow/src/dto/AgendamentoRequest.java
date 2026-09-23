package com.barberflow.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record AgendamentoRequest(@NotNull Integer clienteId, @NotNull Integer profissionalId, @NotNull Integer servicoId, @NotNull LocalDateTime dataHoraInicio) {
    
}
