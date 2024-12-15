package Enos.SpringProject.medVoll.domain.models.patient.dto;

import Enos.SpringProject.medVoll.domain.models.address.dto.UpdateAddressDTO;
import jakarta.validation.constraints.NotNull;

public record UpdatePatientDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        UpdateAddressDTO endereco
) {
}
