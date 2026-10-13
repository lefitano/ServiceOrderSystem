package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.model.Agendamento;
import com.barberFlow.barberFlow.model.Avaliacao;
import com.barberFlow.barberFlow.model.StatusAgendamento;
import com.barberFlow.barberFlow.repository.AgendamentoRepository;
import com.barberFlow.barberFlow.repository.AvaliacaoRepository;
import com.barberFlow.barberFlow.mapper.AvaliacaoMapper;
import com.barberFlow.barberFlow.dto.AvaliacaoRequest;
import com.barberFlow.barberFlow.dto.AvaliacaoResponse;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor 
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public AvaliacaoResponse criar(AvaliacaoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(request.agendamentoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + request.agendamentoId()));

        if (agendamento.getStatusAgendamento() != StatusAgendamento.CONCLUIDO) {
            throw new OperacaoInvalidaException("Só é possível avaliar agendamentos concluídos");
        }

        if (avaliacaoRepository.existsByAgendamentoId(agendamento.getId())) {
            throw new OperacaoInvalidaException("Este agendamento já foi avaliado");
        }

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(request, agendamento);
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        return AvaliacaoMapper.toResponse(avaliacaoSalva);
    }

    public AvaliacaoResponse buscarPorId(Integer id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Avaliação não encontrada com id: " + id));
        return AvaliacaoMapper.toResponse(avaliacao);
    }

    public List<AvaliacaoResponse> listarTodos() {
        return avaliacaoRepository.findAll()
            .stream()
            .map(AvaliacaoMapper::toResponse)
            .toList();
    }

    @Transactional
    public void deletar(Integer id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Avaliação não encontrada com id: " + id);
        }

        avaliacaoRepository.deleteById(id);
    }
}