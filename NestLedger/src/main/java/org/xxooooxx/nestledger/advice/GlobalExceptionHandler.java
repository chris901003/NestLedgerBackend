package org.xxooooxx.nestledger.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xxooooxx.nestledger.common.Response;
import org.xxooooxx.nestledger.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Response<?> handleCustomException(CustomException e) {
        return Response.error(e.getCode(), e.getMessage());
    }
}
