package com.barberflow.repository;

import com.barberflow.model.HorarioDeTrabalho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioDeTrabalhoRepository extends JpaRepository<HorarioDeTrabalho, Integer>{
    List<HorarioDeTrabalho> findByProfissionalId(Integer profissionalId);
}
