package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.model.Agendamento;
import com.barberFlow.barberFlow.model.Cliente;
import com.barberFlow.barberFlow.model.DiaSemana;
import com.barberFlow.barberFlow.model.HorarioDeTrabalho;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.model.Servico;
import com.barberFlow.barberFlow.model.StatusAgendamento;
import com.barberFlow.barberFlow.repository.AgendamentoRepository;
import com.barberFlow.barberFlow.repository.ClienteRepository;
import com.barberFlow.barberFlow.repository.HorarioDeTrabalhoRepository;
import com.barberFlow.barberFlow.repository.ProfissionalRepository;
import com.barberFlow.barberFlow.repository.ServicoRepository;
import com.barberFlow.barberFlow.mapper.AgendamentoMapper;
import com.barberFlow.barberFlow.dto.AgendamentoRequest;
import com.barberFlow.barberFlow.dto.AgendamentoResponse;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;
    private final HorarioDeTrabalhoRepository horarioDeTrabalhoRepository;

    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id: " + request.clienteId()));

        Profissional profissional = profissionalRepository.findByIdParaAgendamento(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        Servico servico = servicoRepository.findById(request.servicoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com id: " + request.servicoId()));

        if (request.dataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new OperacaoInvalidaException("Não é possível agendar em um horário passado");
        }

        LocalDateTime dataHoraFim = request.dataHoraInicio().plusMinutes(servico.getDuracaoMinutos());

        validarExpediente(profissional, request.dataHoraInicio(), dataHoraFim);

        List<Agendamento> conflitos = agendamentoRepository.buscarConflitos(
            profissional.getId(), request.dataHoraInicio(), dataHoraFim);

        if (!conflitos.isEmpty()) {
            throw new OperacaoInvalidaException("Profissional já possui agendamento nesse intervalo de horário");
        }

        if (!agendamentoRepository.buscarConflitosCliente(cliente.getId(), request.dataHoraInicio(), dataHoraFim).isEmpty()) {
            throw new OperacaoInvalidaException("Cliente já possui agendamento nesse intervalo de horário");
        }

        Agendamento agendamento = AgendamentoMapper.toEntity(request, cliente, profissional, servico, dataHoraFim);
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return AgendamentoMapper.toResponse(agendamentoSalvo);
    }

    private void validarExpediente(Profissional profissional, LocalDateTime inicio, LocalDateTime fim) {
        if (!inicio.toLocalDate().equals(fim.toLocalDate())) {
            throw new OperacaoInvalidaException("O agendamento não pode atravessar a meia-noite");
        }

        HorarioDeTrabalho horario = horarioDeTrabalhoRepository
            .findByProfissionalIdAndDiaSemana(profissional.getId(), converterDia(inicio.getDayOfWeek()))
            .orElseThrow(() -> new OperacaoInvalidaException("Profissional não trabalha nesse dia da semana"));

        if (inicio.toLocalTime().isBefore(horario.getHoraInicio()) || fim.toLocalTime().isAfter(horario.getHoraFim())) {
            throw new OperacaoInvalidaException("Horário fora do expediente do profissional ("
                + horario.getHoraInicio() + " às " + horario.getHoraFim() + ")");
        }
    }

    private DiaSemana converterDia(DayOfWeek dia) {
        return switch (dia) {
            case MONDAY -> DiaSemana.SEGUNDA;
            case TUESDAY -> DiaSemana.TERCA;
            case WEDNESDAY -> DiaSemana.QUARTA;
            case THURSDAY -> DiaSemana.QUINTA;
            case FRIDAY -> DiaSemana.SEXTA;
            case SATURDAY -> DiaSemana.SABADO;
            case SUNDAY -> DiaSemana.DOMINGO;
        };
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

    @Transactional
    public AgendamentoResponse atualizarStatus(Integer id, StatusAgendamento novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id));

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CANCELADO
                || agendamento.getStatusAgendamento() == StatusAgendamento.CONCLUIDO) {
            throw new OperacaoInvalidaException("Agendamento " + agendamento.getStatusAgendamento() + " não pode ter o status alterado");
        }

        agendamento.setStatusAgendamento(novoStatus);
        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);

        return AgendamentoMapper.toResponse(agendamentoAtualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + id);
        }

        agendamentoRepository.deleteById(id);
    }
}