package com.barberFlow.barberFlow.mapper;


import com.barberFlow.barberFlow.dto.ProfissionalRequest;
import com.barberFlow.barberFlow.dto.ProfissionalResponse;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.model.Funcao;





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
