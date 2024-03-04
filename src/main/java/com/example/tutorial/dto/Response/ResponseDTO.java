package com.example.tutorial.dto.Response;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private T data;

    public ResponseDTO(T object) {
        this.data = object;
    }

    public static <T> ResponseDTO<T> of(T object) {
        return new ResponseDTO<>(object);
    }
}
