package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;

/**
 * Base exception for HealthCare Management System, supporting AppErrorCodes.
 */
public class AppException extends RuntimeException {
    private final AppErrorCodes errorCode;

    public AppException(AppErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public AppErrorCodes getErrorCode() {
        return errorCode;
    }
}

