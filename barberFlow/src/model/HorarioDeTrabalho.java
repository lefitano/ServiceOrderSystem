package com.barberflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Entity 
@Table(name="horario_trabalho", uniqueConstraints = @UniqueConstraint(name = "uk_profissional_dia", columnNames = {"profissional_id", "dia_semana"}))
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 

public class HorarioDeTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne 
    @JoinColumn(name="profissional_id", nullable = false)
    private Profissional profissional;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 20)
    private DiaSemana diaSemana;

    @Column(name="hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name="hora_fim", nullable = false)
    private LocalTime horaFim;



    
}
