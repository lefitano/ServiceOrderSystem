package com.barberflow.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberflow.model.Agendamento;
import com.barberflow.model.Cliente;
import com.barberflow.model.Profissional;
import com.barberflow.model.Servico;
import com.barberflow.model.StatusAgendamento;
import com.barberflow.repository.AgendamentoRepository;
import com.barberflow.repository.ClienteRepository;
import com.barberflow.repository.ProfissionalRepository;
import com.barberflow.repository.ServicoRepository;
import com.barberflow.mapper.AgendamentoMapper;
import com.barberflow.dto.AgendamentoRequest;
import com.barberflow.dto.AgendamentoResponse;
import com.barberflow.exception.RecursoNaoEncontradoException;
import com.barberflow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoResponse criar(AgendamentoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id: " + request.clienteId()));

        Profissional profissional = profissionalRepository.findById(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        Servico servico = servicoRepository.findById(request.servicoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com id: " + request.servicoId()));

        LocalDateTime dataHoraFim = request.dataHoraInicio().plusMinutes(servico.getDuracaoMinutos());

        List<Agendamento> conflitos = agendamentoRepository.buscarConflitos(
            profissional.getId(), request.dataHoraInicio(), dataHoraFim);

        if (!conflitos.isEmpty()) {
            throw new OperacaoInvalidaException("Profissional já possui agendamento nesse intervalo de horário");
        }

        Agendamento agendamento = AgendamentoMapper.toEntity(request, cliente, profissional, servico, dataHoraFim);
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return AgendamentoMapper.toResponse(agendamentoSalvo);
    }

    public AgendamentoResponse buscarPorId(Integer id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id));
        return AgendamentoMapper.toResponse(agendamento);
    }

    public List<AgendamentoResponse> listarTodos() {
        return agendamentoRepository.findAll()
            .stream()
            .map(AgendamentoMapper::toResponse)
            .toList();
    }

    public List<AgendamentoResponse> listarPorCliente(Integer clienteId) {
        return agendamentoRepository.findByClienteId(clienteId)
            .stream()
            .map(AgendamentoMapper::toResponse)
            .toList();
    }

    public List<AgendamentoResponse> listarPorProfissional(Integer profissionalId) {
        return agendamentoRepository.findByProfissionalId(profissionalId)
            .stream()
            .map(AgendamentoMapper::toResponse)
            .toList();
    }

    public AgendamentoResponse atualizarStatus(Integer id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id));

        agendamento.setStatusAgendamento(novoStatus);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);

        return AgendamentoMapper.toResponse(agendamentoAtualizado);
    }

    public void deletar(Integer id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id);
        }

        agendamentoRepository.deleteById(id);
    }
}