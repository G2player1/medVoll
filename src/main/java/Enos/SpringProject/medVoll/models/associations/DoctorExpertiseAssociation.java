package Enos.SpringProject.medVoll.models.associations;

import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Expertise;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "doctor_expertise")
@Getter
public class DoctorExpertiseAssociation {

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Expertise expertise;

    public DoctorExpertiseAssociation(){}

    public DoctorExpertiseAssociation(Doctor doctor,Expertise expertise){
        this.doctor = doctor;
        this.expertise = expertise;
    }
}
