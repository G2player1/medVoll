package Enos.SpringProject.medVoll.domain.models.dto.reads;

import Enos.SpringProject.medVoll.domain.models.Doctor;

import java.util.List;

public record ReadDetailedDoctorDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        List<String> especialidades,
        ReadAddressDTO endereco
) {
    public ReadDetailedDoctorDTO(Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getTelefone(), doctor.getCrm(), doctor.getExpertises().stream().map(expertise -> expertise.getExpertise().toPortuguese()).toList(), new ReadAddressDTO(doctor.getAddress()));
    }
}


