package com.barberFlow.barberFlow.dto;

import java.time.LocalDateTime;

import com.barberFlow.barberFlow.model.Funcao;



public record UsuarioResponse(Integer id, String nome, String email, Funcao funcao, LocalDateTime criadoEm) {

    }
