package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.models.dto.registers.RegisterPatientDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "name")
    private String name;
    @Column(name = "telefone")
    private String telefone;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;

    public Patient(RegisterPatientDTO registerPatientDTO){
        this.cpf = registerPatientDTO.cpf();
        this.name = registerPatientDTO.nome();
        this.telefone = registerPatientDTO.telefone();
        this.address = new Address(registerPatientDTO.endereco());
    }
}
