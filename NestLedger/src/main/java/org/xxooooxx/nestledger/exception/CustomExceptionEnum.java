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
    USER_AVATAR_NOT_FOUND(2003, "User avatar not found"),

    // Ledger
    LEDGER_NOT_FOUND(3001, "Ledger not found"),
    UNAUTHORIZED_GET_LEDGER(3002, "Unauthorized get ledger"),
    INVALID_CHANGE_MAIN_LEDGER_TITLE(3003, "Invalid change main ledger title"),
    UNAUTHORIZED_UPDATE_LEDGER(3004, "Unauthorized update ledger"),
    UNAUTHORIZED_DELETE_LEDGER(3005, "Unauthorized delete ledger"),
    INVALID_DELETE_MAIN_LEDGER(3006, "Invalid delete main ledger"),

    // Transaction
    TRANSACTION_NOT_FOUND(4001, "Transaction not found"),
    UNAUTHORIZED_OPERATION_ON_TRANSACTION(4002, "Unauthorized operation on transaction"),

    // Tag
    TAG_REFERENCE_LEDGER_NOT_FOUND(5001, "Tag reference ledger not found"),
    UNAUTHORIZED_OPERATION_TAG(5002, "Unauthorized operation tag"),
    TAG_NOT_FOUND(5003, "Tag not found"),;

    @Getter
    final private Integer code;

    @Getter
    final private String message;

    private CustomExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
