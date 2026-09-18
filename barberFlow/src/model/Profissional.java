package com.barberflow.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Entity 
@Table(name="profissional")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 

public class Profissional {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Interger id;

    @OneToOne 
    @JoinColumn(name="usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String especialiade;

    @Column(nullable = false, length = 150)
    private String bio;
}
