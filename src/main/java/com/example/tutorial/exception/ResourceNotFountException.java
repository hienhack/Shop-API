package com.example.tutorial.exception;

public class ResourceNotFountException extends RuntimeException {
    public ResourceNotFountException(String message) {
        super(message);
    }
}
