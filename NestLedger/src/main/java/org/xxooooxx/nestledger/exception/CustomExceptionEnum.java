package org.xxooooxx.nestledger.exception;

import lombok.Getter;

public enum CustomExceptionEnum {

    // Account
    ACCOUNT_AUTHENTICATION_FAILED(1001, "Account authentication failed");

    @Getter
    final private Integer code;

    @Getter
    final private String message;

    private CustomExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
