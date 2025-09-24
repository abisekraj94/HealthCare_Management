package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Exception representing authentication errors in the HealthCare Management System.
 * Used for handling authentication failures and returning appropriate error details.
 */

@Getter
@Setter
public class AuthException extends BaseException {
    public AuthException(AppErrorCodes error) {
        super(error);
    }
}
