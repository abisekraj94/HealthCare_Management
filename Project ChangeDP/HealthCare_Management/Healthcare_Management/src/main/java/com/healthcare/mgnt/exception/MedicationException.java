package com.healthcare.mgnt.exception;

import com.healthcare.mgnt.constants.AppErrorCodes;

/**
 * Exception thrown when a requested medication is not found in the HealthCare Management System.
 */
public class MedicationException extends BaseException {
    public MedicationException(AppErrorCodes error) {
        super(error);
    }
}
