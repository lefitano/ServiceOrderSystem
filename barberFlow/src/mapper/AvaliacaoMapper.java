package com.barberflow.mapper;

import com.barberflow.dto.AvaliacaoResponse;
import com.barberflow.dto.AvaliacaoRequest;
import com.barberflow.model.Avaliacao;
import com.barberflow.model.Agendamento;

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