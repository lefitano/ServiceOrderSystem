package com.barberflow.mapper;

import com.barberflow.model.Usuario;
import com.barberflow.dto.UsuarioResponse;
import com.barberflow.dto.UsuarioRequest;



public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario){
        return new UsuarioResponse (usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getFuncao(), usuario.getCriadoEm());

    }


    public static Usuario toEntity(UsuarioRequest request){
        return Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .funcao(request.funcao())
                .build();
    }
}
