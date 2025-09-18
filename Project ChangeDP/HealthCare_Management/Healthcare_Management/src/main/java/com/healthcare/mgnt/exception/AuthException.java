package com.healthcare.mgnt.exception;
import com.healthcare.mgnt.constants.AppErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Exception representing authentication errors in the HealthCare Management System.
 * Used for handling authentication failures and returning appropriate error details.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthException extends RuntimeException {
    /** Application-specific error code for authentication errors. */
    private String errorCode;

    /** Human-readable error message for authentication errors. */
    private String errorMessage;

    /** HTTP status associated with the authentication error. */
    private HttpStatus status;

    /**
     * Constructs an AuthException using an AppErrorCodes instance.
     * @param error the application error code and details
     */
    public AuthException(AppErrorCodes error) {
        super(error.getErrorMessage());
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
        this.status = error.getStatusCode();
    }
}
