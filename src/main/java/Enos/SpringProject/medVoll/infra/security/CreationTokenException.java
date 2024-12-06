package Enos.SpringProject.medVoll.infra.security;

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
