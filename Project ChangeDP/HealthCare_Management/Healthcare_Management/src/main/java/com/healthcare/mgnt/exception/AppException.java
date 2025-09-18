package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;

/**
 * Base exception for HealthCare Management System, supporting application-specific error codes.
 * Used for custom exception handling throughout the application.
 */
public class AppException extends RuntimeException {
    /** Application-specific error code for this exception. */
    private final AppErrorCodes errorCode;

    /**
     * Constructs a new AppException with the specified error code and message.
     * @param errorCode the application error code
     * @param s the error message
     */
    public AppException(AppErrorCodes errorCode, String s) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    /**
     * Returns the application error code associated with this exception.
     * @return the error code
     */
    public AppErrorCodes getErrorCode() {
        return errorCode;
    }
}
