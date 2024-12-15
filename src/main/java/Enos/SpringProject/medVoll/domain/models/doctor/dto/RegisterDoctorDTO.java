package Enos.SpringProject.medVoll.domain.models.doctor.dto;

import Enos.SpringProject.medVoll.domain.models.address.dto.RegisterAddressDTO;
import Enos.SpringProject.medVoll.domain.models.expertise.dto.RegisterExpertiseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RegisterDoctorDTO(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        @Valid
        List<RegisterExpertiseDTO> especialidades,
        @NotNull
        @Valid
        RegisterAddressDTO endereco
) {
}
