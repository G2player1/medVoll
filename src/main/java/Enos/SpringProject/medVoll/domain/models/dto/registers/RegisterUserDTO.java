package Enos.SpringProject.medVoll.domain.models.dto.registers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotNull
                @NotBlank
        String login,
        @NotNull
                @NotBlank
        String senha
) {
}
