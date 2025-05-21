package org.xxooooxx.nestledger.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.exception.AuthenticationException;
import org.xxooooxx.nestledger.exception.CustomException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Response<?> handleCustomException(CustomException e) {
        return Response.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, message);
        }
        return Response.error(400, "Validation failed", errors);
    }

    @ExceptionHandler
    public ResponseEntity<Response<?>> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(403).body(Response.error(e.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e) {
        System.out.println("Error type: " + e.getClass().getName());
        return Response.error(500, "Internal Server Error", e.getMessage());
    }
}
