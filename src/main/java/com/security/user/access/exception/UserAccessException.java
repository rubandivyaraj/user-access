package com.security.user.access.exception;

import lombok.Getter;

/**
 * UserAccessException class for handling application-specific errors.
 * <p>
 * This exception is used to provide detailed error information in the form of a
 * custom error code, message, and description. It extends
 * {@link RuntimeException}, meaning it is an unchecked exception.
 * </p>
 *
 * @author rubandivyaraj
 */
@Getter
public class UserAccessException extends RuntimeException {

    private static final long serialVersionUID = 6572019145237835730L;

    private UserAccessExceptionCode accessExceptionCode;

    public UserAccessException(UserAccessExceptionCode accessExceptionCode) {
        super(accessExceptionCode.getMessage());
        this.accessExceptionCode = accessExceptionCode;
    }

}
