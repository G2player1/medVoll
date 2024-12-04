package Enos.SpringProject.medVoll.domain.models.dto.registers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterConsultDTO(
        Long medico_id,
        String especialidade,
        @NotNull
        Long paciente_id,
        @NotBlank
        @NotNull
        String horario
) {
}
