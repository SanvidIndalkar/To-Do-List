package com.todo.exceptions;

import com.todo.reponses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFound ex) {

        ApiResponse<String> apiResponse = ApiResponse.<String>builder().success(false)
                .message("Resource not found")
                .data(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalRequestException(IllegalRequestException ex) {

        ApiResponse<String> apiResponse = ApiResponse.<String>builder().success(false)
                .message("Something went wrong")
                .data(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
