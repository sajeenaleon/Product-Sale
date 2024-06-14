package com.productsale.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ProductSaleExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundException(final RuntimeException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }
}
