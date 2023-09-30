package com.example.loginbackend.exception;


public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}