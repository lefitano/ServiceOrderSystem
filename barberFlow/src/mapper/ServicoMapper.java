package com.barberflow.mapper;

import com.barberflow.model.Servico;
import com.barberflow.dto.ServicoRequest;
import com.barberflow.dto.ServicoResponse;

public class ServicoMapper {

    public static ServicoResponse toResponse(Servico servico){
        return new ServicoResponse(servico.getId(), servico.getNome(), servico.getDescricao(), servico.getDuracaoMinutos(), servico.getPreco());
    }

    public static Servico toEntity(ServicoRequest request){
        return Servico.builder()
            .nome(request.nome())
            .descricao(request.descricao())
            .duracaoMinutos(request.duracaoMinutos())
            .preco(request.preco())
            .build();
    }       
    
}
