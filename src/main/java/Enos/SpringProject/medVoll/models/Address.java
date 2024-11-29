package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.dto.registers.RegisterAddressDTO;
import Enos.SpringProject.medVoll.models.dto.updates.UpdateAddressDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cep")
    private Integer cep;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "complemento",length = 2000)
    private String complemento;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Doctor doctor;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private Patient patient;

    public Address(RegisterAddressDTO registerAddressDTO){
        this.logradouro = registerAddressDTO.logradouro();
        this.bairro = registerAddressDTO.bairro();
        this.cidade = registerAddressDTO.cidade();
        this.uf = registerAddressDTO.uf();
        try {
            this.cep = Integer.parseInt(registerAddressDTO.cep());
            this.numero = Integer.parseInt(registerAddressDTO.numero());
        } catch (NumberFormatException e){
            this.cep = null;
            this.numero = null;
            System.out.println(e.getMessage());
        }
        this.complemento = registerAddressDTO.complemento();
    }

    public void setDoctor(Doctor doctor){
        if(doctor == null){
            throw new NullObjectException("the object" + Doctor.class + " is null");
        }
        this.doctor = doctor;
    }

    public void setPatient(Patient patient){
        if(patient == null){
            throw new NullObjectException("the object" + Patient.class + " is null");
        }
        this.patient = patient;
    }

    public void updateData(UpdateAddressDTO updateAddressDTO) {
        if (updateAddressDTO.logradouro() != null){
            this.logradouro = updateAddressDTO.logradouro();
        }
        if (updateAddressDTO.bairro() != null){
            this.bairro = updateAddressDTO.bairro();
        }
        if (updateAddressDTO.cep() != null){
            this.cep = Integer.parseInt(updateAddressDTO.cep());
        }
        if (updateAddressDTO.cidade() != null){
            this.cidade = updateAddressDTO.cidade();
        }
        if (updateAddressDTO.uf() != null){
            this.uf = updateAddressDTO.uf();
        }
        if (updateAddressDTO.numero() != null){
            this.numero = Integer.parseInt(updateAddressDTO.numero());
        }
        if (updateAddressDTO.complemento() != null){
            this.complemento = updateAddressDTO.complemento();
        }
    }
}
