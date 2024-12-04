package Enos.SpringProject.medVoll.domain.models.dto.registers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPatientDTO(
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String telefone,
        @NotNull
        @Valid
        RegisterAddressDTO endereco
) {
}
