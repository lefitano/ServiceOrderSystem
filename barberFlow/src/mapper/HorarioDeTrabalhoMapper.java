package com.barberflow.mapper;
import com.barberflow.model.HorarioDeTrabalho;
import com.barberflow.model.Profissional;



import com.barberflow.dto.HorarioDeTrabalhoResponse;
import com.barberflow.dto.HorarioDeTrabalhoRequest;


public class HorarioDeTrabalhoMapper {
    public static HorarioDeTrabalhoResponse toResponse(HorarioDeTrabalho horario){
        return new HorarioDeTrabalhoResponse(horario.getId(), horario.getProfissional().getId(), horario.getDiaSemana(), horario.getHoraInicio(), horario.getHoraFim());
    }

    public static HorarioDeTrabalho toEntity(HorarioDeTrabalhoRequest request, Profissional profissional){
        return HorarioDeTrabalho.builder()
            .profissional(profissional)
            .diasemana(DiaSemana.diaSemana())
            .horainicio(request.horaInicio())
            .horafim(request.horaFim())
            .build();

    }
    
}
