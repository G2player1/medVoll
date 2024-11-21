package Enos.SpringProject.medVoll.models.dto;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExpertiseDTO {

    private final ExpertiseEnum expertiseEnum;

    public ExpertiseDTO(@NotBlank String especialidade){
        this.expertiseEnum = ExpertiseEnum.fromString(especialidade);
    }

    @Override
    public String toString() {
        return this.expertiseEnum.toString();
    }
}
