package com.platinum.ctacorriente.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.platinum.ctacorriente.errors.TransferenciaException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransferenciaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTransferenciaException(TransferenciaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}