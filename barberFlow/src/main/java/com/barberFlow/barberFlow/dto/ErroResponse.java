package com.barberFlow.barberFlow.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(int status, String mensagem, List<String> detalhes, LocalDateTime timestamp) {

    public ErroResponse(int status, String mensagem) {
        this(status, mensagem, List.of(), LocalDateTime.now());
    }

    public ErroResponse(int status, String mensagem, List<String> detalhes) {
        this(status, mensagem, detalhes, LocalDateTime.now());
    }
}
