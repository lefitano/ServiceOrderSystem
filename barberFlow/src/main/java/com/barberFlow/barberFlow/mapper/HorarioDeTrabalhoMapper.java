package com.barberFlow.barberFlow.mapper;
import com.barberFlow.barberFlow.model.HorarioDeTrabalho;
import com.barberFlow.barberFlow.model.Profissional;



import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoResponse;
import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoRequest;


public class HorarioDeTrabalhoMapper {
    public static HorarioDeTrabalhoResponse toResponse(HorarioDeTrabalho horario){
        return new HorarioDeTrabalhoResponse(horario.getId(), horario.getProfissional().getId(), horario.getDiaSemana(), horario.getHoraInicio(), horario.getHoraFim());
    }

    public static HorarioDeTrabalho toEntity(HorarioDeTrabalhoRequest request, Profissional profissional){
        return HorarioDeTrabalho.builder()
            .profissional(profissional)
            .diaSemana(request.diaSemana())
            .horaInicio(request.horaInicio())
            .horaFim(request.horaFim())
            .build();

    }
    
}
