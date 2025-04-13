package com.ze.financeiro.exception;

import com.ze.financeiro.dto.response.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.warn("EntityNotFoundException: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        request.getRequestURI(),
                        HttpStatus.NOT_FOUND.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        log.warn("Validation failed at {}: {}", request.getRequestURI(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        errors,
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("IllegalArgumentException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        "An unexpected error occurred.",
                        request.getRequestURI(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePaymentNotFound(PaymentNotFoundException ex, HttpServletRequest request) {
        log.warn("Payment not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        request.getRequestURI(),
                        HttpStatus.NOT_FOUND.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(InvalidPaymentStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidStatus(InvalidPaymentStatusException ex, HttpServletRequest request) {
        log.warn("Invalid payment status: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        ex.getMessage(),
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        Instant.now()
                ));
    }

}
