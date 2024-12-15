package Enos.SpringProject.medVoll.domain.models.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReadTokenDTO(@NotNull @NotBlank String token) {
}
