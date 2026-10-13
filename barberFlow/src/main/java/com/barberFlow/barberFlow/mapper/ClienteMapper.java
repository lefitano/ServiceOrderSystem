package com.barberFlow.barberFlow.mapper;

import com.barberFlow.barberFlow.dto.ClienteResponse;
import com.barberFlow.barberFlow.dto.ClienteRequest;
import com.barberFlow.barberFlow.model.Cliente;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.model.Funcao;


public class ClienteMapper {
    public static ClienteResponse toResponse(Cliente cliente){
        return new ClienteResponse(cliente.getId(),cliente.getUsuario().getNome(), cliente.getUsuario().getEmail(), cliente.getTelefone());

    }

    public static Usuario toUsuario(ClienteRequest request){
        return Usuario.builder()
            .nome(request.nome())
            .email(request.email())
            .funcao(Funcao.CLIENTE)
            .build();
    }

    public static Cliente toCliente(ClienteRequest request, Usuario usuarioSalvo){
        return Cliente.builder()
            .usuario(usuarioSalvo)
            .telefone(request.telefone())
            .build();
    }
}
