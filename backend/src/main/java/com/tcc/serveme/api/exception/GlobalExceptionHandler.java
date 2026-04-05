package com.tcc.serveme.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Toda exception relacionado a HTTP a ser adicionada deve extender AppException
    // Desta forma fica centralizado e mais simples o uso

    // a API retorna algo como:
    // {
    //    "status": 404, (http404 not found)
    //    "message": "Recurso não encontrado.",
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

    // Handler para erros de validação do Jakarta (@Valid nos controllers)
    // Captura o primeiro erro de validação encontrado e retorna no mesmo formato do handleAppException
    // Exemplo de retorno:
    // {
    //    "status": 400,
    //    "message": "name: Nome é obrigatório",
    //    "timestamp": "2026-03-12T21:47:17.9413634"
    // }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Requisição inválida");

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}