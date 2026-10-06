package com.barberflow.service;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.barberflow.model.HorarioDeTrabalho;
import com.barberflow.model.Profissional;
import com.barberflow.repository.HorarioDeTrabalhoRepository;
import com.barberflow.repository.ProfissionalRepository;
import com.barberflow.mapper.HorarioDeTrabalhoMapper;
import com.barberflow.dto.HorarioDeTrabalhoRequest;
import com.barberflow.dto.HorarioDeTrabalhoResponse;
import com.barberflow.exception.RecursoNaoEncontradoException;

@Service
@RequiredArgsConstructor
public class HorarioDeTrabalhoService {

    private final HorarioDeTrabalhoRepository horarioDeTrabalhoRepository;
    private final ProfissionalRepository profissionalRepository;

    public HorarioDeTrabalhoResponse criar(HorarioDeTrabalhoRequest request) {
        Profissional profissional = profissionalRepository.findById(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        HorarioDeTrabalho horario = HorarioDeTrabalhoMapper.toEntity(request, profissional);
        HorarioDeTrabalho horarioSalvo = horarioDeTrabalhoRepository.save(horario);

        return HorarioDeTrabalhoMapper.toResponse(horarioSalvo);
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

    public HorarioDeTrabalhoResponse atualizar(Integer id, HorarioDeTrabalhoRequest request) {
        HorarioDeTrabalho horario = horarioDeTrabalhoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Horário de trabalho não encontrado com id: " + id));

        Profissional profissional = profissionalRepository.findById(request.profissionalId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + request.profissionalId()));

        horario.setProfissional(profissional);
        horario.setDiaSemana(request.diaSemana());
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFim(request.horaFim());

        HorarioDeTrabalho horarioAtualizado = horarioDeTrabalhoRepository.save(horario);
        return HorarioDeTrabalhoMapper.toResponse(horarioAtualizado);
    }

    public void deletar(Integer id) {
        if (!horarioDeTrabalhoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Horário de trabalho não encontrado com id: " + id);
        }

        horarioDeTrabalhoRepository.deleteById(id);
    }
}