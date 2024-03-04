package com.example.tutorial.dto.Response;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private Object errors;

    public ErrorResponseDTO(Object errors) {
        this.errors = errors;
    }
}
