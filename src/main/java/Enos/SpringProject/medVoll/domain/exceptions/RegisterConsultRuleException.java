package Enos.SpringProject.medVoll.domain.exceptions;

public class RegisterConsultRuleException extends RuntimeException {

    private final String message;

    public RegisterConsultRuleException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return """
                Error: %s
                Regras:
                "1 - Agendamentos apenas de segunda a sabado
                "2 - Agendamento apenas em horario de funcionamento: das 07:00 ás 19:00
                "3 - Agendamento deve ter pelo menos 30 min de antecedencia
                "4 - Um paciente paciente só pode agendar uma consulta no dia
                """.formatted(message);
    }
}
