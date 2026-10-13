package com.barberFlow.barberFlow.mapper;

import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.dto.UsuarioResponse;
import com.barberFlow.barberFlow.dto.UsuarioRequest;



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
