package com.barberflow.dto;
import java.time.LocalTime;

import com.barberflow.model.DiaSemana;

import jakarta.validation.constraints.NotNull; 

public record HorarioDeTrabalhoRequest(@NotNull  Integer profissionalId, @NotNull DiaSemana diaSemana, @NotNull LocalTime horaInicio, @NotNull LocalTime horaFim ) {
    
}
