package Enos.SpringProject.medVoll.models.dto.registers;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterExpertiseDTO {

    private final ExpertiseEnum expertiseEnum;

    public RegisterExpertiseDTO(@NotBlank String especialidade){
        this.expertiseEnum = ExpertiseEnum.fromString(especialidade);
    }

    @Override
    public String toString() {
        return this.expertiseEnum.toString();
    }
}
