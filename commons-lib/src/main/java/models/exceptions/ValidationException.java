package models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationException extends StandardError{

    private List<FieldError> errors;

    @Getter
    @AllArgsConstructor
    private static class FieldError {
        private String fieldName;
        private String message;
    }

    public void addError(String fieldName, String message) {
        FieldError fieldError = new FieldError(fieldName, message);
        this.errors.add(fieldError);
    }
}
