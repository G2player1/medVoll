package Enos.SpringProject.medVoll.domain.exceptions;

public class CreationTokenException extends RuntimeException {

    private final String message;

    public CreationTokenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
