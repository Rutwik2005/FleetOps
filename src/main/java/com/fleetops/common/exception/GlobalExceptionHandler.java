package com.fleetops.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fleetops.vehicle.exception.VehicleAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
        
	}
	@ExceptionHandler(FleetAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleFleetAlreadyExistsException(
	        FleetAlreadyExistsException ex,
	        HttpServletRequest request) {

	    ApiError error = new ApiError(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            HttpStatus.CONFLICT.getReasonPhrase(),
	            ex.getMessage(),
	            request.getRequestURI());

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFound(
	        ResourceNotFoundException ex,
	        HttpServletRequest request) {

	    ApiError error = new ApiError(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            HttpStatus.NOT_FOUND.getReasonPhrase(),
	            ex.getMessage(),
	            request.getRequestURI());

	    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(VehicleAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleVehicleAlreadyExists(
	        VehicleAlreadyExistsException ex,
	        HttpServletRequest request) {

	    ApiError error = new ApiError(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            HttpStatus.CONFLICT.getReasonPhrase(),
	            ex.getMessage(),
	            request.getRequestURI());

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
}
