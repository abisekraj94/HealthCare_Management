package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;
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
     * Handles validation errors and returns field-specific messages.
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity containing a map of field errors and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        logger.warn("Validation failed: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom AppException and returns structured error response.
     * @param ex the AppException thrown in the application
     * @return ResponseEntity containing ApiErrorResponse and the appropriate HTTP status
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(AppException ex) {
        AppErrorCodes code = ex.getErrorCode();
        logger.error("AppException: {} - {}", code.getErrorCode(), code.getErrorMessage());
        ApiErrorResponse response = new ApiErrorResponse(code.getErrorCode(), code.getErrorMessage(), code.getStatusCode());
        return new ResponseEntity<>(response, code.getStatusCode());
    }

    /**
     * Handles runtime exceptions and returns generic error response.
     * @param ex the RuntimeException thrown in the application
     * @param request the current web request
     * @return ResponseEntity containing ApiErrorResponse and HTTP 400 status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("RuntimeException: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse("GEN-001", ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions and returns internal server error response.
     * @param ex the Exception thrown in the application
     * @param request the current web request
     * @return ResponseEntity containing ApiErrorResponse and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled Exception: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse("GEN-002", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
