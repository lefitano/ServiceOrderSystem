package com.barberflow.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.barberflow.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    List<Agendamento> findByProfissionalId(Integer profissionalId);
    List<Agendamento> findByClienteId(Integer clienteId);

    @Query ("""
        SELECT a FROM Agendamento a
        WHERE a.profissional.id = :profissionalId
        AND a.dataHoraInicio < :fim
        AND a.dataHoraFim > :inicio
    """)
    List<Agendamento> buscarConflitos(
        @Param ("profissionalId") Integer profissionalId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

}