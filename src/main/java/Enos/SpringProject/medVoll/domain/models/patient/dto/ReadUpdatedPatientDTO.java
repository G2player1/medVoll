package Enos.SpringProject.medVoll.domain.models.patient.dto;

import Enos.SpringProject.medVoll.domain.models.patient.Patient;
import Enos.SpringProject.medVoll.domain.models.address.dto.ReadAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadUpdatedPatientDTO {

    private Long id;
    private String nome;
    private String telefone;
    private ReadAddressDTO endereco;

    public ReadUpdatedPatientDTO(Patient patient){
        this.id = patient.getId();
        this.nome = patient.getName();
        this.telefone = patient.getTelefone();
        this.endereco = new ReadAddressDTO(patient.getAddress());
    }
}
