package com.security.user.access.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler class for centralized exception handling in Spring
 * Boot.
 * 
 * we can use ControllerAdvice which contains RestControllerAdvice also
 * 
 * @author Your Name
 */
@ControllerAdvice
public class UserAccessExceptionHandler {

	@ExceptionHandler(UserAccessException.class)
	public ResponseEntity<UserAccessErrorResponse> handleUserAccessException(UserAccessException exception) {
		UserAccessErrorResponse response = UserAccessErrorResponse.builder()
				.code(exception.getAccessExceptionCode().getCode()).error(exception.getAccessExceptionCode().getError())
				.message(exception.getAccessExceptionCode().getMessage()).build();
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<UserAccessErrorResponse> handleException(Exception exception, WebRequest request) {
		UserAccessErrorResponse response = UserAccessErrorResponse.builder().code("EXE").error("Exception")
				.message(exception.getMessage()).build();
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);

	}

}
