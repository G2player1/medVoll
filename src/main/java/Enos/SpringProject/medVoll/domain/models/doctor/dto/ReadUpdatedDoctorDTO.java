package Enos.SpringProject.medVoll.domain.models.doctor.dto;

import Enos.SpringProject.medVoll.domain.models.doctor.Doctor;
import Enos.SpringProject.medVoll.domain.models.address.dto.ReadAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadUpdatedDoctorDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private List<String> especialidades;
    private ReadAddressDTO endereco;

    public ReadUpdatedDoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.nome = doctor.getName();
        this.email = doctor.getEmail();
        this.telefone = doctor.getTelefone();
        this.crm = doctor.getCrm();
        this.especialidades = doctor.getExpertises()
                .stream()
                .map(expertise -> expertise.getExpertise().toPortuguese())
                .toList();
        this.endereco = new ReadAddressDTO(doctor.getAddress());
    }
}
