package com.barberflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barberflow.model.Avaliacao;


public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>{
    Optional<Avaliacao> findByAgendamentoId(Integer avaliacaoId);
}