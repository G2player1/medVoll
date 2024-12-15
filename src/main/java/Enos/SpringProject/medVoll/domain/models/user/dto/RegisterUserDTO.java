package Enos.SpringProject.medVoll.domain.models.user.dto;

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
