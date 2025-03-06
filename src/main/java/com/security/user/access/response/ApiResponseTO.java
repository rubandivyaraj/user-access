package com.security.user.access.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponseTO<T> implements Serializable {
    private final static Long serialId = 1232343455464561L;

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponseTO(HttpStatus status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
