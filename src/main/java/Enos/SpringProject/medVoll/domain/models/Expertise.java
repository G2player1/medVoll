package Enos.SpringProject.medVoll.domain.models;

import Enos.SpringProject.medVoll.domain.enums.ExpertiseEnum;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterExpertiseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
    @Setter
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;
    @Column(name = "active",nullable = false)
    private Integer active;


    public Expertise(RegisterExpertiseDTO registerExpertiseDTO){
        this.expertise = registerExpertiseDTO.getExpertiseEnum();
        this.active = 1;
    }

    public Expertise(Doctor doctor,RegisterExpertiseDTO registerExpertiseDTO){
        this.expertise = registerExpertiseDTO.getExpertiseEnum();
        this.doctor = doctor;
        this.active = 1;
    }

    public boolean isActive(){
        return active == 1;
    }

    public void expertiseDeleteLogical() {
        this.active = 0;
    }
}
