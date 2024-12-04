package Enos.SpringProject.medVoll.domain.exceptions;

public class RegisterConsultRuleException extends RuntimeException {

    private final String message;

    public RegisterConsultRuleException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
