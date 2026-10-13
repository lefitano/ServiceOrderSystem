package com.barberFlow.barberFlow.dto;

import java.time.LocalTime;
import com.barberFlow.barberFlow.model.DiaSemana;


public record HorarioDeTrabalhoResponse(Integer id, Integer profissionalId, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim) {
    
}
