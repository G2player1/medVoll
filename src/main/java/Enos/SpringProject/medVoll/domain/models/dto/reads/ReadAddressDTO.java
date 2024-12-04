package Enos.SpringProject.medVoll.domain.models.dto.reads;

import Enos.SpringProject.medVoll.domain.models.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadAddressDTO {

    private Long id;
    private String logradouro;
    private String bairro;
    private Integer cep;
    private String cidade;
    private String uf;
    private Integer numero;
    private String complemento;

    public ReadAddressDTO(Address address){
        this.id = address.getId();
        this.logradouro = address.getLogradouro();
        this.bairro = address.getBairro();
        this.cep = address.getCep();
        this.cidade = address.getCidade();
        this.uf = address.getUf();
        this.numero = address.getNumero();
        this.complemento = address.getComplemento();
    }
}
