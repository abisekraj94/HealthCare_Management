package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception for user not found scenarios in HealthCare Management System.
 */
@Getter
@Setter
public class UserException extends BaseException {
    public UserException(AppErrorCodes error) {
        super(error);
    }
}
