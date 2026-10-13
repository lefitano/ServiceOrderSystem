package com.barberFlow.barberFlow.mapper;

import java.time.LocalDateTime;

import com.barberFlow.barberFlow.dto.AgendamentoRequest;
import com.barberFlow.barberFlow.dto.AgendamentoResponse;
import com.barberFlow.barberFlow.model.Agendamento;
import com.barberFlow.barberFlow.model.StatusAgendamento;
import com.barberFlow.barberFlow.model.Cliente;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.model.Servico;

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