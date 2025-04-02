package com.security.user.access.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler class for centralized exception handling in Spring
 * Boot.
 * <p>
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
        return new ResponseEntity<>(response, exception.getAccessExceptionCode().getCode());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserAccessErrorResponse> handleException(Exception exception, WebRequest request) {
        UserAccessErrorResponse response = UserAccessErrorResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).error("Exception")
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * This Methods handle the spring-starter-validation error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserAccessErrorResponse> handleValidationException(
            MethodArgumentNotValidException validException) {

        String message = validException.getAllErrors().stream().map(error -> error.getDefaultMessage())
                .filter(Objects::nonNull).collect(Collectors.joining(", "));

        UserAccessErrorResponse response = UserAccessErrorResponse.builder().code(HttpStatus.PRECONDITION_FAILED).error("Expectation Failed")
                .message(message).build();
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);

    }

}
