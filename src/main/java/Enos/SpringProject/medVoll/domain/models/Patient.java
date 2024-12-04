package Enos.SpringProject.medVoll.domain.models;

import Enos.SpringProject.medVoll.domain.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterPatientDTO;
import Enos.SpringProject.medVoll.domain.models.dto.updates.UpdatePatientDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "telefone")
    private String telefone;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Address address;
    @OneToMany(mappedBy = "patient",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Consult> consults;
    @Column(name = "active")
    private Integer active;

    public Patient(RegisterPatientDTO registerPatientDTO){
        this.consults = new ArrayList<>();
        this.cpf = registerPatientDTO.cpf();
        this.name = registerPatientDTO.nome();
        this.email = registerPatientDTO.email();
        this.telefone = registerPatientDTO.telefone();
        this.active = 1;
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

    public void patientDeleteLogical(){
        this.active = 0;
    }

    public void updateData(UpdatePatientDTO updatePatientDTO) {
        if(updatePatientDTO.nome() != null){
            this.name = updatePatientDTO.nome();;
        }
        if(updatePatientDTO.telefone() != null){
            this.telefone = updatePatientDTO.telefone();
        }
        if(updatePatientDTO.endereco() != null){
            this.address.updateData(updatePatientDTO.endereco());
        }
    }
}
