package Enos.SpringProject.medVoll.models;

import Enos.SpringProject.medVoll.exceptions.NullObjectException;
import Enos.SpringProject.medVoll.models.dto.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "addresses")
@Getter
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
    @OneToOne(mappedBy = "address",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Doctor doctor;

    public Address(){}

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
}
