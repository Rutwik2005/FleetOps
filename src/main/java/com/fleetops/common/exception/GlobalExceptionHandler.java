package com.fleetops.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fleetops.vehicle.exception.VehicleAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==============================
    // Email Already Exists
    // ==============================
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    // ==============================
    // Fleet Already Exists
    // ==============================
    @ExceptionHandler(FleetAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleFleetAlreadyExists(
            FleetAlreadyExistsException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    // ==============================
    // Vehicle Already Exists
    // ==============================
    @ExceptionHandler(VehicleAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleVehicleAlreadyExists(
            VehicleAlreadyExistsException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    // ==============================
    // Duplicate Resource
    // ==============================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    // ==============================
    // Resource Not Found
    // ==============================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    // ==============================
    // Business Rule Violations
    // ==============================
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ApiError> handleBusinessRuleViolation(
            BusinessRuleViolationException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, ex, request);
    }

    // ==============================
    // Validation Errors (@Valid)
    // ==============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // ==============================
    // Generic Exception
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex,
            HttpServletRequest request) {

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex,
                request);
    }

    // ==============================
    // Common Error Builder
    // ==============================
    private ResponseEntity<ApiError> buildErrorResponse(
            HttpStatus status,
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(error, status);
    }
}