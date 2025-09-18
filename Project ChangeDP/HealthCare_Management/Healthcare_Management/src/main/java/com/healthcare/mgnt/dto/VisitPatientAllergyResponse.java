package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for visit patient allergy response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientAllergyResponse {
    private Long allergyId;
    private Long visitId;
    private String allergyName;
    private String reaction;
    private String severity;
    private String notes;

}

