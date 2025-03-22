package com.security.user.access.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAccessErrorResponse {

	private String code;
	private String error;
	private String message;

}
