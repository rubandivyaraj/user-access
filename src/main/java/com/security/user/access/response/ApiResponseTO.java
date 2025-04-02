package com.security.user.access.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponseTO<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public ApiResponseTO(HttpStatus status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message == null ? status.getReasonPhrase() : message;
        this.data = data;
    }

}
