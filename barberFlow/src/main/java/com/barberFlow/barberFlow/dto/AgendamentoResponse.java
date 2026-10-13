package com.barberFlow.barberFlow.dto;

import com.barberFlow.barberFlow.model.StatusAgendamento;
import java.time.LocalDateTime;

public record AgendamentoResponse(Integer id, Integer clienteId, String nomeCliente, Integer profissionalId, String nomeProfissional, Integer servicoId, String nomeServico, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, StatusAgendamento statusAgendamento, LocalDateTime criadoEm) {
    
}
