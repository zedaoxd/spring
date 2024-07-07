package br.com.bruno.userserviceapi.controller.exceptions;

import models.exceptions.StandardError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex, final HttpServletRequest request) {
        return ResponseEntity
            .status(NOT_FOUND)
            .body(StandardError.builder()
                    .timestamp(LocalDateTime.now())
                    .status(NOT_FOUND.value())
                    .error(NOT_FOUND.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(request.getRequestURI())
                    .build()
            );
    }
}
