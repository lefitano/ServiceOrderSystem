package com.barberflow.dto;

import java.time.LocalDateTime;

import com.barberflow.model.Funcao;



public record UsuarioResponse(Integer id, String nome, String email, Funcao funcao, LocalDateTime criadoEm) {

    }
