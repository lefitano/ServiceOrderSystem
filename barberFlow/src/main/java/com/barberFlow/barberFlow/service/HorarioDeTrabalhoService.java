package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.model.HorarioDeTrabalho;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.repository.HorarioDeTrabalhoRepository;
import com.barberFlow.barberFlow.repository.ProfissionalRepository;
import com.barberFlow.barberFlow.mapper.HorarioDeTrabalhoMapper;
import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoRequest;
import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoResponse;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor
public class HorarioDeTrabalhoService {

    private final HorarioDeTrabalhoRepository horarioDeTrabalhoRepository;
    private final ProfissionalRepository profissionalRepository;

    @Transactional
    public HorarioDeTrabalhoResponse criar(HorarioDeTrabalhoRequest request) {
        Profissional profissional = profissionalRepository.findById(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        validarHorario(request);
        if (horarioDeTrabalhoRepository.existsByProfissionalIdAndDiaSemana(profissional.getId(), request.diaSemana())) {
            throw new OperacaoInvalidaException("Profissional já possui horário cadastrado para " + request.diaSemana());
        }

        HorarioDeTrabalho horario = HorarioDeTrabalhoMapper.toEntity(request, profissional);
        HorarioDeTrabalho horarioSalvo = horarioDeTrabalhoRepository.save(horario);

        return HorarioDeTrabalhoMapper.toResponse(horarioSalvo);
    }

    private void validarHorario(HorarioDeTrabalhoRequest request) {
        if (!request.horaInicio().isBefore(request.horaFim())) {
            throw new OperacaoInvalidaException("A hora de início deve ser anterior à hora de fim");
        }
    }

    public HorarioDeTrabalhoResponse buscarPorId(Integer id) {
        HorarioDeTrabalho horario = horarioDeTrabalhoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Horário de trabalho não encontrado com id: " + id));
        return HorarioDeTrabalhoMapper.toResponse(horario);
    }

    public List<HorarioDeTrabalhoResponse> listarTodos() {
        return horarioDeTrabalhoRepository.findAll()
            .stream()
            .map(HorarioDeTrabalhoMapper::toResponse)
            .toList();
    }

    public List<HorarioDeTrabalhoResponse> listarPorProfissional(Integer profissionalId) {
        return horarioDeTrabalhoRepository.findByProfissionalId(profissionalId)
            .stream()
            .map(HorarioDeTrabalhoMapper::toResponse)
            .toList();
    }

    @Transactional
    public HorarioDeTrabalhoResponse atualizar(Integer id, HorarioDeTrabalhoRequest request) {
        HorarioDeTrabalho horario = horarioDeTrabalhoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Horário de trabalho não encontrado com id: " + id));

        Profissional profissional = profissionalRepository.findById(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        validarHorario(request);
        if (horarioDeTrabalhoRepository.existsByProfissionalIdAndDiaSemanaAndIdNot(profissional.getId(), request.diaSemana(), id)) {
            throw new OperacaoInvalidaException("Profissional já possui horário cadastrado para " + request.diaSemana());
        }

        horario.setProfissional(profissional);
        horario.setDiaSemana(request.diaSemana());
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFim(request.horaFim());

        HorarioDeTrabalho horarioAtualizado = horarioDeTrabalhoRepository.save(horario);
        return HorarioDeTrabalhoMapper.toResponse(horarioAtualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!horarioDeTrabalhoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Horário de trabalho não encontrado com id: " + id);
        }

        horarioDeTrabalhoRepository.deleteById(id);
    }
}