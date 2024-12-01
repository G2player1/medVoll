package Enos.SpringProject.medVoll.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consults")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Patient patient;
    @Column(name = "schedule_start")
    private LocalDateTime scheduleStart;
    @Column(name = "schedule_end")
    private LocalDateTime scheduleEnd;
    @Column(name = "active")
    private Integer active;

    public Consult(Doctor doctor,Patient patient, LocalDateTime schedule){
        this.doctor = doctor;
        this.patient = patient;
        this.scheduleStart = schedule;
        this.scheduleEnd = schedule.plusHours(1);
        this.active = 1;
    }

    public void consultDeleteLogical() {
        this.active = 0;
    }
}
