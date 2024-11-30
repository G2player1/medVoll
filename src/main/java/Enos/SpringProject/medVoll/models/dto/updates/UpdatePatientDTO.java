package Enos.SpringProject.medVoll.models.dto.updates;

import jakarta.validation.constraints.NotNull;

public record UpdatePatientDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        UpdateAddressDTO endereco
) {
}
