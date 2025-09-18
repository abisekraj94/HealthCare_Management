package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for visit patient allergy creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientAllergyRequest {
    @NotNull(message = "Visit ID is required")
    private Long visitId;

    @NotBlank(message = "Allergy name is required")
    private String allergyName;

    private String reaction;
    private String severity;
    private String notes;

}
