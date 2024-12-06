package Enos.SpringProject.medVoll.domain.enums;

import Enos.SpringProject.medVoll.domain.exceptions.CantGetEnumException;

public enum UserTypeEnum {
    DOCTOR("medico"),
    PACIENTE("paciente");

    private final String portugueseType;

    UserTypeEnum(String portugueseType){
        this.portugueseType = portugueseType;
    }

    public static UserTypeEnum fromString(String value){
        for (UserTypeEnum e : UserTypeEnum.values()){
            if(e.toPortuguese().equalsIgnoreCase(value)){
                return e;
            }
        }
        throw new CantGetEnumException("cant get enum by passed value");
    }

    public String toPortuguese(){
        return this.portugueseType;
    }
}
