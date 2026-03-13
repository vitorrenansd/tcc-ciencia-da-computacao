package com.tcc.serveme.api.exception;

import org.springframework.http.HttpStatus;

// Toda exception da API a ser adicionada deve extender AppException
// Desta forma fica centralizado e mais simples o uso
// Veja o uso dela na classe GlobalExceptionHandler
public abstract class AppException extends RuntimeException {
    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}