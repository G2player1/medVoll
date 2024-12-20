package Enos.SpringProject.medVoll.domain.models.address.dto;

import jakarta.validation.constraints.Pattern;

public record UpdateAddressDTO(
        String logradouro,
        String bairro,
        @Pattern(regexp = "\\d{4,8}")
        String cep,
        String cidade,
        String uf,
        String numero,
        String complemento
) {
}
