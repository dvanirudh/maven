package com.example.exceptions;


import com.example.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

    ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors.toString());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
}

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Constraint Validation ", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalExceptions(Exception ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), " Check the exception ", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}