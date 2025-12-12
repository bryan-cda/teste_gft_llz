package com.example.pessoa.domain;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
