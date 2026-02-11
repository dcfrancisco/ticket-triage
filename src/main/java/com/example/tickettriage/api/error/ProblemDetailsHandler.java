package com.example.tickettriage.api.error;

import com.example.tickettriage.application.service.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class ProblemDetailsHandler {

    private static final URI DEFAULT_TYPE = URI.create("about:blank");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                .toList();
        ProblemResponse body = new ProblemResponse(DEFAULT_TYPE, "Validation failed", HttpStatus.BAD_REQUEST.value(),
                "Request validation failed", URI.create(request.getRequestURI()), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraintViolation(ConstraintViolationException ex,
            HttpServletRequest request) {
        List<ValidationError> errors = ex.getConstraintViolations().stream()
                .map(v -> new ValidationError(v.getPropertyPath().toString(), v.getMessage()))
                .toList();
        ProblemResponse body = new ProblemResponse(DEFAULT_TYPE, "Validation failed", HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), URI.create(request.getRequestURI()), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        ProblemResponse body = ProblemResponse.of(DEFAULT_TYPE, "Not Found", HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemResponse> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        ProblemResponse body = ProblemResponse.of(DEFAULT_TYPE, "Conflict", HttpStatus.CONFLICT.value(),
                ex.getMessage(), URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        ProblemResponse body = ProblemResponse.of(DEFAULT_TYPE, "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), URI.create(request.getRequestURI()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(body);
    }
}
