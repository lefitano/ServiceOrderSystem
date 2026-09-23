package com.barberflow.dto;

import java.time.LocalTime;
import com.barberflow.model.DiaSemana;


public record HorarioDeTrabalhoResponse(Integer id, Integer profissionalId, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim) {
    
}
