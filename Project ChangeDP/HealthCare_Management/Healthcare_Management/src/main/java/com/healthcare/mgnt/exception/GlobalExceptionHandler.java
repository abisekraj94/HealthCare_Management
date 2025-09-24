package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.dto.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for HealthCare Management System.
 * Handles validation, custom, and generic exceptions with structured error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles all other exceptions and returns internal server error response.
     * @param ex the Exception thrown in the application
     * @return ResponseEntity containing ApiErrorResponse and HTTP 500 status
     */
    @ExceptionHandler(BaseException.class)
    public BaseResponse<Void> handleBaseException(BaseException ex) {
        logger.error("BaseException: {} - {}", ex.getErrorCode(), ex.getErrorMessage());
        return new BaseResponse<>(false, ex.getErrorMessage(), null);
    }

    /**
     * Handles all other exceptions and returns internal server error response.
     * @param ex the Exception thrown in the application
     * @return ResponseEntity containing ApiErrorResponse and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled Exception: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse("GEN-002", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
