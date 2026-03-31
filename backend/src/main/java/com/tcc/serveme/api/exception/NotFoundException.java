package com.tcc.serveme.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {
    public NotFoundException() {
        super("Recurso não encontrado.", HttpStatus.NOT_FOUND);
    }
    // Sem mensagem customizada para não expor parte das tabelas do backend
}