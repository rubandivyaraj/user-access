package com.security.user.access.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

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
