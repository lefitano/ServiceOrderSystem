package com.barberFlow.barberFlow.dto;

import com.barberFlow.barberFlow.model.Funcao;

public record LoginResponse(String token, String tipo, long expiraEmSegundos, Integer usuarioId, String nome, String email, Funcao funcao, Integer clienteId, Integer profissionalId) {
}
