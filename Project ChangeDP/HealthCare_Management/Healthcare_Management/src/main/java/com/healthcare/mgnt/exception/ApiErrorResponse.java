package com.healthcare.mgnt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Standard API error response for HealthCare Management System.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {
    private String errorCode;
    private String errorMessage;
    private HttpStatus status;
}

