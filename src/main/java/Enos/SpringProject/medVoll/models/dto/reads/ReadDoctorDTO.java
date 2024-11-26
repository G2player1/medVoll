package Enos.SpringProject.medVoll.models.dto.reads;

import Enos.SpringProject.medVoll.models.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReadDoctorDTO {

    private Long id;
    private String nome;
    private String email;
    private String crm;
    private List<String> especialidade;

    public ReadDoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.nome = doctor.getName();
        this.email = doctor.getEmail();
        this.crm = doctor.getCrm();
        especialidade = doctor.getExpertises().stream()
                .map(doctorExpertise -> doctorExpertise
                        .getExpertise()
                        .toPortuguese())
                .toList();
    }
}
