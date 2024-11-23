package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.associations.DoctorExpertiseAssociation;
import Enos.SpringProject.medVoll.models.dto.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.models.dto.UpdateDoctorDTO;
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
    private List<DoctorExpertiseAssociation> expertises;
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

    public void addExpertises(DoctorExpertiseAssociation expertise){
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
        if (!updateDoctorDTO.nome().isBlank()){
            this.name = updateDoctorDTO.nome();
        }
        if (!updateDoctorDTO.email().isBlank()){
            this.email = updateDoctorDTO.email();
        }
        if (!updateDoctorDTO.telefone().isBlank()){
            this.telefone = updateDoctorDTO.telefone();
        }
        if (!updateDoctorDTO.crm().isBlank()){
            this.crm = updateDoctorDTO.crm();
        }
        if (updateDoctorDTO.endereco() != null){
            this.address.updateData(updateDoctorDTO.endereco());
        }
        if (updateDoctorDTO.especialidades() != null){
            this.expertises = updateDoctorDTO.especialidades()
                    .stream()
                    .map(expertiseDTO -> new DoctorExpertiseAssociation(this,new Expertise(expertiseDTO)))
                    .toList();
            this.expertises.forEach(doctorExpertise -> doctorExpertise.getExpertise().add(doctorExpertise));
        }
    }
}