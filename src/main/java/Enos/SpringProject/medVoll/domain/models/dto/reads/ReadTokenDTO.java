package Enos.SpringProject.medVoll.domain.models.dto.reads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReadTokenDTO(@NotNull @NotBlank String token) {
}
