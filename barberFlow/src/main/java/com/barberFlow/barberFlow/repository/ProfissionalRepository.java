package com.barberFlow.barberFlow.repository;

import com.barberFlow.barberFlow.model.Profissional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;


public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByUsuarioId(Integer usuarioId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Profissional p WHERE p.id = :id")
    Optional<Profissional> findByIdParaAgendamento(@Param("id") Integer id);
}
