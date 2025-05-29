package com.productService.productService.exceptions;

import com.productService.productService.exceptions.customExceptions.ProductExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class GlobalExceptionHandler {
    // Manejo de errores de validación de argumentos (por ejemplo, errores de @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    // Manejo de errores personalizados
    @ExceptionHandler(ProductExceptions.class)
    public ResponseEntity<?> handleProductExceptions(ProductExceptions ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }


}
