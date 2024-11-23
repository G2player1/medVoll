package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.dto.AddressDTO;
import Enos.SpringProject.medVoll.models.dto.UpdateAddressDTO;
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

    public Address(AddressDTO addressDTO){
        this.logradouro = addressDTO.logradouro();
        this.bairro = addressDTO.bairro();
        this.cidade = addressDTO.cidade();
        this.uf = addressDTO.uf();
        try {
            this.cep = Integer.parseInt(addressDTO.cep());
            this.numero = Integer.parseInt(addressDTO.numero());
        } catch (NumberFormatException e){
            this.cep = null;
            this.numero = null;
            System.out.println(e.getMessage());
        }
        this.complemento = addressDTO.complemento();
    }

    public void setDoctor(Doctor doctor){
        if(doctor == null){
            throw new NullObjectException("the object" + this.getClass() + " is null");
        }
        this.doctor = doctor;
    }

    public void updateData(UpdateAddressDTO updateAddressDTO) {
        if (!updateAddressDTO.logradouro().isBlank()){
            this.logradouro = updateAddressDTO.logradouro();
        }
        if (!updateAddressDTO.bairro().isBlank()){
            this.bairro = updateAddressDTO.bairro();
        }
        if (!updateAddressDTO.cep().isBlank()){
            this.cep = Integer.parseInt(updateAddressDTO.cep());
        }
        if (!updateAddressDTO.cidade().isBlank()){
            this.cidade = updateAddressDTO.cidade();
        }
        if (!updateAddressDTO.uf().isBlank()){
            this.uf = updateAddressDTO.uf();
        }
        if (!updateAddressDTO.numero().isBlank()){
            this.numero = Integer.parseInt(updateAddressDTO.numero());
        }
        if (!updateAddressDTO.complemento().isBlank()){
            this.complemento = updateAddressDTO.complemento();
        }
    }
}
