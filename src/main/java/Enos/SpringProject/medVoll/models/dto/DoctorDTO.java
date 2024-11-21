package Enos.SpringProject.medVoll.models.dto;

public record DoctorDTO(String nome, String email, String crm,
                        ExpertiseDTO especialidade, AddressDTO endereco) {
}
