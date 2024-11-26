package Enos.SpringProject.medVoll.models.dto.updates;

import Enos.SpringProject.medVoll.enums.ExpertiseEnum;
import lombok.Getter;

@Getter
public class UpdateExpertiseDTO {

    private final ExpertiseEnum expertiseEnum;

    public UpdateExpertiseDTO(String especialidade){
        this.expertiseEnum = ExpertiseEnum.fromString(especialidade);
    }

    @Override
    public String toString() {
        return this.expertiseEnum.toString();
    }
}
