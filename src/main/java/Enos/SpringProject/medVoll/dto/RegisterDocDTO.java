package Enos.SpringProject.medVoll.dto;

public record RegisterDocDTO(String nome, String email, String crm,
                             ExpertiseDTO especialidade, AddressDTO endereco) {
}
