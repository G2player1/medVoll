package Enos.SpringProject.medVoll.infra;

import Enos.SpringProject.medVoll.domain.exceptions.CantGetEnumException;
import Enos.SpringProject.medVoll.domain.exceptions.RegisterConsultRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class, CantGetEnumException.class})
    public ResponseEntity handleError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorValidationData::new).toList());
    }

    @ExceptionHandler(RegisterConsultRuleException.class)
    public ResponseEntity handleRegisterConsultRuleException(RegisterConsultRuleException e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    private record ErrorValidationData(String field, String message){
        public ErrorValidationData(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
