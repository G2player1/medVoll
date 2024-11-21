package Enos.SpringProject.medVoll.models.dto;

import Enos.SpringProject.medVoll.models.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DoctorListingDataDTO {

    private String nome;
    private String email;
    private String crm;
    private List<String> especialidade;

    public DoctorListingDataDTO(Doctor doctor){
        this.nome = doctor.getName();
        this.email = doctor.getEmail();
        this.crm = doctor.getCrm();
        especialidade = doctor.getExpertises().stream()
                .map(doctorExpertise -> doctorExpertise
                        .getExpertise()
                        .getExpertise()
                        .toPortuguese())
                .toList();
    }
}
