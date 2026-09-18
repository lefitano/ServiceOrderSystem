package com.barberflow.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;


@Entity 
@Table(name="servico")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder



public class Servico {

    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(nullable = false, length = 100)
private String nome;

@Column( nullable = false, length = 100)
private String descricao;

@Column(name = "duracao_minutos", nullable = false)
private Integer duracaoMinutos;

@Column( nullable = false, precision = 10, scale = 2)
private BigDecimal preco;


}
