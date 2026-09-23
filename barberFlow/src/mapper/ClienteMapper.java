package com.barberflow.mapper;

import com.barberflow.dto.ClienteResponse;
import com.barberflow.dto.ClienteRequest;
import com.barberflow.model.Cliente;
import com.barberflow.model.Usuario;
import com.barberflow.model.Funcao;


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
