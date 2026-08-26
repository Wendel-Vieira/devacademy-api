package com.dio.devacademy.controller.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id) {
        super("Recurso não encontrado com o ID: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
