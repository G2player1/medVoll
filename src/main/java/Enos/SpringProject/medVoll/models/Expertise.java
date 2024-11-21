package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.associations.DoctorExpertiseAssociation;
import Enos.SpringProject.medVoll.models.dto.ExpertiseDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "expertises")
@Getter
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "expertise",nullable = false)
    private ExpertiseEnum expertise;
    @OneToMany(mappedBy = "expertise",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<DoctorExpertiseAssociation> doctors;

    public Expertise(){}

    public Expertise(ExpertiseDTO expertiseDTO){
        this.expertise = expertiseDTO.getExpertiseEnum();
    }

    public void add(DoctorExpertiseAssociation doctor){
        if(doctor == null){
            throw new NullObjectException("the object" + this.getClass() + " is null");
        }
        this.doctors.add(doctor);
    }
}
