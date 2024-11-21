package Enos.SpringProject.medVoll.enums;

import Enos.SpringProject.medVoll.exceptions.CantGetEnumException;

public enum Expertise {
    ORTHOPEDY("ortopedia"),
    CARDIOLOGY("cardiologia"),
    GYNECOLOGY("ginecologia"),
    DERMATOLOGY("dermatologia");

    private String expertise;

    Expertise(String expertise){
        this.expertise = expertise;
    }

    public static Expertise fromString(String value){
        for (Expertise e : Expertise.values()){
            if(e.expertise.equalsIgnoreCase(value)){
                return e;
            }
        }
        throw new CantGetEnumException("cant get enum by passed value");
    }
}
