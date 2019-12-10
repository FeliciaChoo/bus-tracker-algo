package com.paulohva.bustracker.api.exception;

import com.paulohva.bustracker.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Not found", e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StandardError> dateTimeParseError(DateTimeParseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Date and/or Time invalid", e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}