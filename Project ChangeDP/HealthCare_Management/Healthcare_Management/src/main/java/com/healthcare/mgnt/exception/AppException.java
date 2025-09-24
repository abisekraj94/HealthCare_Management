package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Base exception for HealthCare Management System, supporting application-specific error codes.
 * Used for custom exception handling throughout the application.
 */
@Getter
@Setter
public class AppException extends BaseException {
    public AppException(AppErrorCodes error) {
        super(error);
    }
}
