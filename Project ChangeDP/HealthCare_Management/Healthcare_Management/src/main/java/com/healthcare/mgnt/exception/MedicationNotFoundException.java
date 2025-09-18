package com.healthcare.mgnt.exception;

/**
 * Exception thrown when a requested medication is not found in the HealthCare Management System.
 */
public class MedicationNotFoundException extends RuntimeException {
    /**
     * Constructs a new MedicationNotFoundException with the specified detail message.
     * @param message the detail message
     */
    public MedicationNotFoundException(String message) {
        super(message);
    }
}
