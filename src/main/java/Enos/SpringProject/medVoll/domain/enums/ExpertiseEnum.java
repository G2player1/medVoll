package Enos.SpringProject.medVoll.domain.enums;

import Enos.SpringProject.medVoll.domain.exceptions.CantGetEnumException;

public enum ExpertiseEnum {
    ORTHOPEDY("ortopedia"),
    CARDIOLOGY("cardiologia"),
    GYNECOLOGY("ginecologia"),
    DERMATOLOGY("dermatologia");

    private String expertise;

    ExpertiseEnum(String expertise){
        this.expertise = expertise;
    }

    public static ExpertiseEnum fromString(String value){
        for (ExpertiseEnum e : ExpertiseEnum.values()){
            if(e.expertise.equalsIgnoreCase(value)){
                return e;
            }
        }
        throw new CantGetEnumException("cant get enum by passed value");
    }

    public String toPortuguese(){
        return this.expertise;
    }
}
