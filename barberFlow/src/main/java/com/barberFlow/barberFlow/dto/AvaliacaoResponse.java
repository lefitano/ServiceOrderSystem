package com.barberFlow.barberFlow.dto;

import java.time.LocalDateTime;

public record AvaliacaoResponse(Integer id, Integer agendamentoId, Integer nota, String comentario, LocalDateTime criadoEm ) {
    
}
