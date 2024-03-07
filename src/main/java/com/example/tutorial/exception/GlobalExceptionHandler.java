package com.example.tutorial.exception;

import com.example.tutorial.dto.Response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDTO> handleBindException(BindException e) {
        String message = "";
        for (var error : e.getFieldErrors()) {
            String newLine = error.getField() + " = " + error.getRejectedValue() +
                    " is invalid: " + error.getDefaultMessage();
            message = message + newLine + ". ";
        }
        message = message.substring(0, message.length() - 1);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 0, message)));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(BusinessException e) {
        HttpStatus status = e.getStatusCode() == null ? HttpStatus.NOT_FOUND : HttpStatus.valueOf(e.getStatusCode());
        return ResponseEntity.status(status)
                .body(new ErrorResponseDTO(new ErrorResponse(e.getStatusCode(), 0, e.getMessage())));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ErrorResponseDTO handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ErrorResponseDTO(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), 0, "Method not found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        String message = "Internal server error";
        // Todo: log the error
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0, message)));
    }

}
