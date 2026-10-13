package com.barberFlow.barberFlow.repository;

import com.barberFlow.barberFlow.model.DiaSemana;
import com.barberFlow.barberFlow.model.HorarioDeTrabalho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioDeTrabalhoRepository extends JpaRepository<HorarioDeTrabalho, Integer>{
    List<HorarioDeTrabalho> findByProfissionalId(Integer profissionalId);
    boolean existsByProfissionalIdAndDiaSemana(Integer profissionalId, DiaSemana diaSemana);
    boolean existsByProfissionalIdAndDiaSemanaAndIdNot(Integer profissionalId, DiaSemana diaSemana, Integer id);
}
