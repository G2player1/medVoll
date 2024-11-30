package Enos.SpringProject.medVoll.models.dto.registers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RegisterPatientDTO(
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Valid
        RegisterAddressDTO endereco
) {
}
