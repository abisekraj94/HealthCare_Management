package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;

/**
 * Custom exception for user not found scenarios in HealthCare Management System.
 */
public class UserNotFoundException extends AppException {
    public UserNotFoundException() {
        super(AppErrorCodes.USER_NOT_FOUND);
    }
}

