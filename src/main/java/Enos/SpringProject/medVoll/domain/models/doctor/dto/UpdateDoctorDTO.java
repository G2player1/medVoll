package Enos.SpringProject.medVoll.domain.models.doctor.dto;

import Enos.SpringProject.medVoll.domain.models.address.dto.UpdateAddressDTO;
import Enos.SpringProject.medVoll.domain.models.expertise.dto.RegisterExpertiseDTO;
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
