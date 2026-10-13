package com.barberFlow.barberFlow.mapper;

import com.barberFlow.barberFlow.dto.AvaliacaoResponse;
import com.barberFlow.barberFlow.dto.AvaliacaoRequest;
import com.barberFlow.barberFlow.model.Avaliacao;
import com.barberFlow.barberFlow.model.Agendamento;

public class AvaliacaoMapper {
    public static AvaliacaoResponse toResponse(Avaliacao avaliacao){
        return new AvaliacaoResponse(avaliacao.getId(), avaliacao.getAgendamento().getId(), avaliacao.getNota(), avaliacao.getComentario(), avaliacao.getCriadoEm());
    }

    public static Avaliacao toEntity(AvaliacaoRequest request, Agendamento agendamento){
        return Avaliacao.builder()
            .agendamento(agendamento)
            .nota(request.nota())
            .comentario(request.comentario())
            .build();
    }
}