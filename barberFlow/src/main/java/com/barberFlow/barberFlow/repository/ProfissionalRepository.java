package com.barberFlow.barberFlow.repository;

import com.barberFlow.barberFlow.model.Profissional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByUsuarioId(Integer usuarioId);
}
