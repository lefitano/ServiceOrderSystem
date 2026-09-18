package com.barberflow.repository;

import com.barberflow.model.Profissional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByUsuarioId(Integer usuarioId);
}
