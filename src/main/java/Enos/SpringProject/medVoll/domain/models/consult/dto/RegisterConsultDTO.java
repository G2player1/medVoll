package Enos.SpringProject.medVoll.domain.models.consult.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterConsultDTO(
        Long medico_id,
        @NotNull
        String especialidade,
        @NotNull
        Long paciente_id,
        @NotBlank
        @NotNull
        String horario
) {
}
