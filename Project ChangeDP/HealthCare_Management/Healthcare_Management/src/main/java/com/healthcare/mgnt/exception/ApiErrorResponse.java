package com.healthcare.mgnt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Standard API error response for HealthCare Management System.
 * Used to encapsulate error details returned to clients.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {
    /** Application-specific error code. */
    private String errorCode;

    /** Human-readable error message. */
    private String errorMessage;

    /** HTTP status associated with the error. */
    private HttpStatus status;
}
