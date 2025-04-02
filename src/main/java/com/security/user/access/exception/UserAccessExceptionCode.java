package com.security.user.access.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum to define all possible error codes, messages, and descriptions for
 * maintainability.
 *
 * @author rubandivyaraj
 */
@Getter
public enum UserAccessExceptionCode {

    USER_INVALID(HttpStatus.NOT_FOUND, "User Invalid", "User not found"),
    USER_EXIST(HttpStatus.CONFLICT, "User Exists", "User already registered"),
    TOKEN_INVALID(HttpStatus.NOT_ACCEPTABLE, "Token Invalid", "Token invalid or expired"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "Token Not Found", "Token invalid or missing"),
    USER_LOCKED(HttpStatus.NOT_ACCEPTABLE, "User Locked", "User is locked, Try after some-time"),
    USER_BLOCKED(HttpStatus.FORBIDDEN, "User Blocked", "User blocked permanently, Contact administrator");

    private final HttpStatus code;
    private final String error;
    private final String message;

    UserAccessExceptionCode(HttpStatus code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
}
