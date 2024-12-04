package Enos.SpringProject.medVoll.domain.exceptions;

public class RequestBdException extends RuntimeException {

    private final String message;

    public RequestBdException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
