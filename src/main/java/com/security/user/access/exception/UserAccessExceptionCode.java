package com.security.user.access.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum to define all possible error codes, messages, and descriptions for
 * maintainability.
 * 
 * @author rubandivyaraj
 */
@Getter
public enum UserAccessExceptionCode {

	USER_INVALID("UA1000", "User Invalid", "User not found"),
	USER_EXIST("UA1001", "User Exists", "User already registered"),
	USER_LOCKED("UA1002", "User Locked", "User is locked, Try after some-time"),
	USER_BLOCKED("UA1003", "User Blocked", "User blocked permenantly, Contact administrator");

	private final String code;
	private final String error;
	private final String message;

	UserAccessExceptionCode(String code, String error, String message) {
		this.code = code;
		this.error = error;
		this.message = message;
	}
}
