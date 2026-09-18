package com.barberflow.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity 
@Table(name="agendamento")
@Builder 
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor 

public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne 
    @JoinColumn(name="cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne 
    @JoinColumn(name="profissional_id", nullable = false)
    private Profissional profissional;

    @ManyToOne 
    @JoinColumn(name="servico_id", nullable = false)
    private Servico servico;

    @Column(name="data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name="data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    @Column(name="status_agendamento", nullable = false, length = 20)
    private StatusAgendamento statusAgendamento;

    @Column(name="criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist 
    protected void onCreate(){
        this.criadoEm = LocalDateTime.now();
        if(this.statusAgendamento == null){
            this.statusAgendamento = statusAgendamento.AGENDADO;
        }
    }
    
}


