package Enos.SpringProject.medVoll.models.dto;

public record AddressDTO(String logradouro,String bairro, String cep, String cidade,
                         String uf, String numero, String complemento) {
}
