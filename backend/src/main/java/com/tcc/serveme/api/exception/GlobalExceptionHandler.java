package com.tcc.serveme.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Toda exception da API a ser adicionada deve extender AppException
    // Desta forma fica centralizado e mais simples o uso

    // a API retorna algo como:
    // {
    //    "status": 404,
    //    "message": "Nenhum turno aberto no momento.",
    //    "timestamp": "2026-03-12T21:47:17.9413634"
    // }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getStatus().value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}