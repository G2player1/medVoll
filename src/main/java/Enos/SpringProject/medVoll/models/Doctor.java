package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdateDoctorDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "telefone",unique = true)
    private String telefone;
    @Column(name = "crm")
    private String crm;
    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expertise> expertises;
    @OneToOne(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;

    public Doctor(RegisterDoctorDTO registerDoctorDTO){
        expertises = new ArrayList<>();
        this.name = registerDoctorDTO.nome();
        this.email = registerDoctorDTO.email();
        this.telefone = registerDoctorDTO.telefone();
        this.crm = registerDoctorDTO.crm();
    }

    public void addExpertises(Expertise expertise){
        if(expertise == null){
            throw new NullObjectException("the object" + this.getClass() + " is null");
        }
        this.expertises.add(expertise);
    }

    public void setAddress(Address address){
        if(address == null){
            throw new NullObjectException("the object" + this.getClass() + " is null");
        }
        this.address = address;
    }

    public void updateData(UpdateDoctorDTO updateDoctorDTO) {
        if (updateDoctorDTO.nome() != null){
            if(!(updateDoctorDTO.nome().isEmpty() && updateDoctorDTO.nome().isBlank())){
                this.name = updateDoctorDTO.nome();
            }
        }
        if (updateDoctorDTO.email() != null){
            if(!(updateDoctorDTO.email().isEmpty() && updateDoctorDTO.email().isBlank())) {
                this.email = updateDoctorDTO.email();
            }
        }
        if (updateDoctorDTO.telefone() != null){
            if(!(updateDoctorDTO.telefone().isEmpty() && updateDoctorDTO.telefone().isBlank())) {
                this.telefone = updateDoctorDTO.telefone();
            }
        }
        if (updateDoctorDTO.crm() != null){
            if(!(updateDoctorDTO.crm().isEmpty() && updateDoctorDTO.crm().isBlank())){
                this.crm = updateDoctorDTO.crm();
            }
        }
        if (updateDoctorDTO.endereco() != null){
            this.address.updateData(updateDoctorDTO.endereco());
        }
        if (updateDoctorDTO.especialidades() != null){
            this.expertises = updateDoctorDTO.especialidades()
                    .stream()
                    .map(registerExpertiseDTO -> new Expertise(this,registerExpertiseDTO))
                    .toList();
        }
    }
}