package Enos.SpringProject.medVoll.domain.models.doctor;

import Enos.SpringProject.medVoll.domain.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.domain.models.expertise.Expertise;
import Enos.SpringProject.medVoll.domain.models.address.Address;
import Enos.SpringProject.medVoll.domain.models.consult.Consult;
import Enos.SpringProject.medVoll.domain.models.doctor.dto.RegisterDoctorDTO;
import Enos.SpringProject.medVoll.domain.models.doctor.dto.UpdateDoctorDTO;
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
    @OneToOne(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;
    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expertise> expertises;
    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Consult> consults;
    @Column(name = "active",nullable = false)
    private Integer active;

    public Doctor(RegisterDoctorDTO registerDoctorDTO){
        expertises = new ArrayList<>();
        consults = new ArrayList<>();
        this.name = registerDoctorDTO.nome();
        this.email = registerDoctorDTO.email();
        this.telefone = registerDoctorDTO.telefone();
        this.crm = registerDoctorDTO.crm();
        this.active = 1;
    }

    public void addExpertises(Expertise expertise){
        if(expertise == null){
            throw new NullObjectException("the object" + Expertise.class + " is null");
        }
        this.expertises.add(expertise);
    }

    public void addConsult(Consult consult){
        if(consult == null){
            throw new NullObjectException("the object" + Consult.class + " is null");
        }
        this.consults.add(consult);
    }

    public void setAddress(Address address){
        if(address == null){
            throw new NullObjectException("the object" + Address.class + " is null");
        }
        this.address = address;
    }

    public void doctorDeleteLogical(){
        this.active = 0;
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
            List<Expertise> aux = updateDoctorDTO.especialidades()
                    .stream()
                    .map(registerExpertiseDTO -> new Expertise(this,registerExpertiseDTO))
                    .toList();
            this.expertises.forEach(Expertise::expertiseDeleteLogical);
            this.expertises = aux;
        }
    }
}