package com.example.tutorial.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private Integer code;
    private String message;
}
