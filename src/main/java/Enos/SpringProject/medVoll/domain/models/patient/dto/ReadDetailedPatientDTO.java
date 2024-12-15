package Enos.SpringProject.medVoll.domain.models.patient.dto;

import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import Enos.SpringProject.medVoll.domain.models.address.dto.ReadAddressDTO;
import Enos.SpringProject.medVoll.domain.models.consult.dto.ReadConsultDTO;

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
