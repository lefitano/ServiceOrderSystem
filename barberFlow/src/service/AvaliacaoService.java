package com.barberflow.service;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberflow.model.Agendamento;
import com.barberflow.model.Avaliacao;
import com.barberflow.model.StatusAgendamento;
import com.barberflow.repository.AgendamentoRepository;
import com.barberflow.repository.AvaliacaoRepository;
import com.barberflow.mapper.AvaliacaoMapper;
import com.barberflow.dto.AvaliacaoRequest;
import com.barberflow.dto.AvaliacaoResponse;
import com.barberflow.exception.RecursoNaoEncontradoException;
import com.barberflow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AgendamentoRepository agendamentoRepository;

    public AvaliacaoResponse criar(AvaliacaoRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(request.agendamentoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado com id: " + request.agendamentoId()));

        if (agendamento.getStatusAgendamento() != StatusAgendamento.CONCLUIDO) {
            throw new OperacaoInvalidaException("Só é possível avaliar agendamentos concluídos");
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

    public void deletar(Integer id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Avaliação não encontrada com id: " + id);
        }

        avaliacaoRepository.deleteById(id);
    }
}