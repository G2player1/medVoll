package Enos.SpringProject.medVoll.models.dto.registers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterConsultDTO(
        Long medicoId,
        String especialidade,
        @NotNull
        Long pacienteId,
        @NotBlank
        @NotNull
        String horario
) {
}
