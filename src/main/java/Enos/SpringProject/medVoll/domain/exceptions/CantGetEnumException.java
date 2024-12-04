package Enos.SpringProject.medVoll.domain.exceptions;

public class CantGetEnumException extends RuntimeException {

    private final String message;

    public CantGetEnumException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
