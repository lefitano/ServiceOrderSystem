package com.barberflow.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity 
@Table(name="avaliacao")
@Builder 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 


public class Avaliacao {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne 
    @JoinColumn(name="agendamento_id", nullable = false, unique = true)
    private Agendamento agendamento;

    @Column(nullable = false)
    private Integer nota;

    @Column(length = 255)
    private String comentario;

    @Column(name="criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;


    @PrePersist 
    protected void onCreate(){
        this.criadoEm = LocalDateTime.now();
    }


}
