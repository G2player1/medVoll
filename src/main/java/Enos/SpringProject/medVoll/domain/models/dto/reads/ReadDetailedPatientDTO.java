package Enos.SpringProject.medVoll.domain.models.dto.reads;

import Enos.SpringProject.medVoll.domain.models.Patient;

import java.util.List;

public record ReadDetailedPatientDTO(
        Long id,
        String cpf,
        String name,
        String email,
        String telefone,
        ReadAddressDTO address,
        List<ReadConsultDTO> consults
) {
    public ReadDetailedPatientDTO(Patient patient){
        this(patient.getId(), patient.getCpf(), patient.getName(), patient.getEmail(), patient.getTelefone(), new ReadAddressDTO(patient.getAddress()), patient.getConsults().stream().map(ReadConsultDTO::new).toList());
    }
}
