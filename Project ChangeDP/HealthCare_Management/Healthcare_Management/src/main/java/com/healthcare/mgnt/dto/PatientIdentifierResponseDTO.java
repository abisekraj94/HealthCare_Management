package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for patient identifier response data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientIdentifierResponseDTO {
    private Long identifierId;
    private Long patientId;
    private String identifierType;
    private String identifierValue;

    // Getters and setters
    // ...
}

