package br.com.bruno.authserviceapi.controller.exception;

import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ BadCredentialsException.class, RefreshTokenExpired.class })
    ResponseEntity<StandardError> handleBadCredentialsException(
            RuntimeException ex,
            final HttpServletRequest request
    ) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(UNAUTHORIZED.value())
                        .error(UNAUTHORIZED.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandardError> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            final HttpServletRequest request
    ) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            final HttpServletRequest request
    ) {
        var error = ValidationException.builder()
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message("Validation error")
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(error);
    }
}
