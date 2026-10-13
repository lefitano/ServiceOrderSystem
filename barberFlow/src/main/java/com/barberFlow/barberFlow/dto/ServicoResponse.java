package com.barberFlow.barberFlow.dto;

import java.math.BigDecimal;

public record ServicoResponse(Integer id, String nome, String descricao, Integer duracaoMinutos, BigDecimal preco) {

    
}
