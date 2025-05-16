package org.xxooooxx.nestledger.exception;

import lombok.Getter;

public enum CustomExceptionEnum {

    // Common
    MISSING_PARAMETER(901, "Missing parameter"),

    // Account
    ACCOUNT_AUTHENTICATION_FAILED(1001, "Account authentication failed"),

    // User
    USER_INFO_NOT_FOUND(2001, "User info not found"),
    UNAUTHORIZED_UPDATE_USER_INFO(2002, "Unauthorized update user info"),
    USER_AVATAR_NOT_FOUND(2003, "User avatar not found"),;

    @Getter
    final private Integer code;

    @Getter
    final private String message;

    private CustomExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
