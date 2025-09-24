package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException {
    /** Application-specific error code for Application errors. */
    private String errorCode;

    /** Human-readable error message for Application errors. */
    private String errorMessage;

    /** HTTP status associated with the Application error. */
    private HttpStatus status;

    public BaseException(AppErrorCodes error) {
        super(error.getErrorMessage());
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
        this.status = error.getStatusCode();
    }
}
