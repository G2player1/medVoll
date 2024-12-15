package Enos.SpringProject.medVoll.domain.models.patient.dto;

import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadPatientDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    public ReadPatientDTO(Patient patient){
        this.id = patient.getId();
        this.nome = patient.getName();
        this.email = patient.getEmail();
        this.telefone = patient.getTelefone();
    }
}
