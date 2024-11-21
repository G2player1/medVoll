package Enos.SpringProject.medVoll.dto;

import Enos.SpringProject.medVoll.enums.Expertise;

public class ExpertiseDTO {

    private final Expertise expertise;

    public ExpertiseDTO(String especialidade){
        this.expertise = Expertise.fromString(especialidade);
    }
}
