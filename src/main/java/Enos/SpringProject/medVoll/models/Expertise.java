package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterExpertiseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expertises")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "expertise",nullable = false)
    private ExpertiseEnum expertise;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;

    public Expertise(RegisterExpertiseDTO registerExpertiseDTO){
        this.expertise = registerExpertiseDTO.getExpertiseEnum();
    }

    public Expertise(Doctor doctor,RegisterExpertiseDTO registerExpertiseDTO){
        this.expertise = registerExpertiseDTO.getExpertiseEnum();
        this.doctor = doctor;
    }


    public void setDoctor(Doctor doctor){
        if(doctor == null){
            throw new NullObjectException("the object" + this.getClass() + " is null");
        }
        this.doctor = doctor;
    }
}
