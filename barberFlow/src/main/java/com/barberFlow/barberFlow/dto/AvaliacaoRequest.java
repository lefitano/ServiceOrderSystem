package com.barberFlow.barberFlow.dto;



import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AvaliacaoRequest(@NotNull Integer agendamentoId, @NotNull @Min(1) @Max(5) Integer nota, @Size(max = 255) String comentario) {
    
}
