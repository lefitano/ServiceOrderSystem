package com.barberflow.mapper;

import java.time.LocalDateTime;

import com.barberflow.dto.AgendamentoRequest;
import com.barberflow.dto.AgendamentoResponse;
import com.barberflow.model.Agendamento;
import com.barberflow.model.StatusAgendamento;
import com.barberflow.model.Cliente;
import com.barberflow.model.Profissional;
import com.barberflow.model.Servico;

public class AgendamentoMapper {
    public static AgendamentoResponse toResponse(Agendamento agendamento){
        return new AgendamentoResponse(agendamento.getId(),agendamento.getCliente().getId()
                , agendamento.getCliente().getUsuario().getNome(),agendamento.getProfissional().getId()
                , agendamento.getProfissional().getUsuario().getNome(),agendamento.getServico().getId(), agendamento.getServico().getNome()
                , agendamento.getDataHoraInicio(),agendamento.getDataHoraFim()
                , agendamento.getStatusAgendamento(), agendamento.getCriadoEm());
    }

    public static Agendamento toEntity(AgendamentoRequest request, Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHoraFim){
        return Agendamento.builder()
            .cliente(cliente)
            .profissional(profissional)
            .servico(servico)
            .dataHoraInicio(request.dataHoraInicio())
            .dataHoraFim(dataHoraFim)
            .statusAgendamento(StatusAgendamento.AGENDADO)
            .build();
    }
    
}