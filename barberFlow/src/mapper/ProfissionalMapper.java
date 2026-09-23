package com.barberflow.mapper;


import com.barberflow.dto.ProfissionalRequest;
import com.barberflow.dto.ProfissionalResponse;
import com.barberflow.model.Usuario;
import com.barberflow.model.Profissional;
import com.barberflow.model.Funcao;





public class ProfissionalMapper {
    public static ProfissionalResponse toResponse(Profissional profissional){
        return new ProfissionalResponse(profissional.getId(), profissional.getUsuario().getNome(), profissional.getUsuario().getEmail(), profissional.getEspecialidade(), profissional.getBio());
    }

    public static Usuario toUsuario(ProfissionalRequest request){
        return Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .funcao(Funcao.PROFISSIONAL)
                .build();
    }
    public static Profissional toProfissional(ProfissionalRequest request, Usuario usuarioSalvo){
        return Profissional.builder()
            .usuario(usuarioSalvo)
            .especialidade(request.especialidade())
            .bio(request.bio())
            .build();
    }



    
}
