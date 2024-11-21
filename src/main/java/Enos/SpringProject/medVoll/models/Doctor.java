package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.associations.DoctorExpertiseAssociation;
import Enos.SpringProject.medVoll.models.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "crm")
    private String crm;
    @OneToMany(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<DoctorExpertiseAssociation> expertises;
    @OneToOne(mappedBy = "doctor",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Address address;

    public Doctor(){}

    public Doctor(DoctorDTO doctorDTO){
        this.name = doctorDTO.nome();
        this.email = doctorDTO.email();
        this.crm = doctorDTO.crm();
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

}
