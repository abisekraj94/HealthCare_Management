package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for patient identifier creation/update requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientIdentifierRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotBlank(message = "Identifier type is required")
    private String identifierType;

    @NotBlank(message = "Identifier value is required")
    private String identifierValue;

}
