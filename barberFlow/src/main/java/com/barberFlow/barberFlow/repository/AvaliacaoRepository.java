package com.barberFlow.barberFlow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barberFlow.barberFlow.model.Avaliacao;


public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>{
    Optional<Avaliacao> findByAgendamentoId(Integer agendamentoId);
    boolean existsByAgendamentoId(Integer agendamentoId);
}