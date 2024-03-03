package com.example.tutorial.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private Integer statusCode;
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

}
