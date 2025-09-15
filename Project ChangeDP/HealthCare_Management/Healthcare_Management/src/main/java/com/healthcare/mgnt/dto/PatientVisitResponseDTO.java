package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * DTO for patient visit response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientVisitResponseDTO {
    private Long visitId;
    private Long patientId;
    private Timestamp visitDate;
    private String visitType;
    private Long physicianId;
    private String notes;

    // Getters and setters
    // ...
}

