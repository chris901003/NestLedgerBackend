package org.xxooooxx.nestledger.exception;


import lombok.Getter;

public class CustomException extends RuntimeException{
    @Getter
    final private Integer code;

    @Getter
    final private String message;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CustomException(CustomExceptionEnum customExceptionEnum) {
        super(customExceptionEnum.getMessage());
        this.code = customExceptionEnum.getCode();
        this.message = customExceptionEnum.getMessage();
    }
}
