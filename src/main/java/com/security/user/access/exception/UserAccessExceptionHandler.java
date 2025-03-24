package com.security.user.access.exception;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
		UserAccessErrorResponse response = UserAccessErrorResponse.builder().code("UA500").error("Exception")
				.message(exception.getMessage()).build();
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);

	}

	
	/**
	 * This Methods handle the spring-starter-validation error messages 
	 * */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<UserAccessErrorResponse> handleValidationException(
			MethodArgumentNotValidException validException) {
		String message = validException.getAllErrors().stream().map(error -> error.getDefaultMessage())
				.filter(Objects::nonNull).collect(Collectors.joining(", "));

		UserAccessErrorResponse response = UserAccessErrorResponse.builder().code("UA417").error("Expectation Failed")
				.message(message).build();
		return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

	}

}
