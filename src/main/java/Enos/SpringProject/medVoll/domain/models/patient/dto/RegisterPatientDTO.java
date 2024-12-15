package Enos.SpringProject.medVoll.domain.models.patient.dto;

import Enos.SpringProject.medVoll.domain.models.address.dto.RegisterAddressDTO;
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
