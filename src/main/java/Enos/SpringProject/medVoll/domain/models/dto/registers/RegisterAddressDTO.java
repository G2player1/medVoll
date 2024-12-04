package Enos.SpringProject.medVoll.domain.models.dto.registers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterAddressDTO(
        @NotBlank
        String logradouro,
        @NotBlank
        String bairro,
        @NotBlank
        @Pattern(regexp = "\\d{4,8}")
        String cep,
        @NotBlank
        String cidade,
        @NotBlank
        String uf,
        @NotBlank
        String numero,
        String complemento
) {
}
