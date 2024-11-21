package Enos.SpringProject.medVoll.models.associations;

import Enos.SpringProject.medVoll.models.Doctor;
import Enos.SpringProject.medVoll.models.Expertise;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor_expertise")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DoctorExpertiseAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Expertise expertise;

    public DoctorExpertiseAssociation(Doctor doctor,Expertise expertise){
        this.doctor = doctor;
        this.expertise = expertise;
    }
}
