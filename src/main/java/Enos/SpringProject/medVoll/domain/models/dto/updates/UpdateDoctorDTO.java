package Enos.SpringProject.medVoll.domain.models.dto.updates;

import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterExpertiseDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateDoctorDTO (
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        List<RegisterExpertiseDTO> especialidades,
        UpdateAddressDTO endereco
){
}
