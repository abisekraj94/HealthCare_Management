package com.healthcare.mgnt.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Application-wide error codes for structured error responses in HealthCare Management System.
 */
@Getter
public enum AppErrorCodes {
    USER_NOT_FOUND("USER-003", "User not found", HttpStatus.ACCEPTED),
    ROLE_NOT_FOUND("ROLE-001", "Role not found", HttpStatus.ACCEPTED),
    PATIENT_NOT_FOUND("PAT-001", "Patient not found", HttpStatus.NOT_FOUND),
    VISIT_NOT_FOUND("VIS-001", "Visit not found", HttpStatus.NOT_FOUND),
    MEDICAL_HISTORY_NOT_FOUND("MEDH-001", "Medical history not found", HttpStatus.NOT_FOUND),
    IDENTIFIER_NOT_FOUND("IDF-001", "Identifier not found", HttpStatus.NOT_FOUND),
    VITAL_SIGN_NOT_FOUND("VIT-001", "Vital sign not found", HttpStatus.NOT_FOUND),
    DOCUMENT_NOT_FOUND("DOC-001", "Document not found", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_FOUND("PERM-001", "Permission not found", HttpStatus.NOT_FOUND),
    ASSIGNMENT_NOT_FOUND("ASSIGN-001", "Assignment not found", HttpStatus.NOT_FOUND),
    ALLERGY_NOT_FOUND("ALG-001", "Allergy not found", HttpStatus.NOT_FOUND),
    DIAGNOSIS_NOT_FOUND("DIAG-001", "Diagnosis not found", HttpStatus.NOT_FOUND),
    TOKEN_HAS_EXPIRED("TOK-001","Token has expired",HttpStatus.UNAUTHORIZED),
    TOKEN_HAS_INVALID("TOK-002","Invalid token",HttpStatus.UNAUTHORIZED),
    INTERNAL_ERROR("GEN-001", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus statusCode;

    AppErrorCodes(String errorCode, String errorMessage, HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }
}
