package com.security.user.access.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAccessErrorResponse {

    private HttpStatus code;
    private String error;
    private String message;

}
