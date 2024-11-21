package Enos.SpringProject.medVoll.dto;

import Enos.SpringProject.medVoll.enums.Expertise;

public record RegisterDocDTO(String nome, String email, String crm,
                             Expertise especialidade, AddressDTO endereco) {
}
