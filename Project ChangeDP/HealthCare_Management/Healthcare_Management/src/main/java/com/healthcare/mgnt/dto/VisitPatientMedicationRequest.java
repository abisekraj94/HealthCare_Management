package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.sql.Date;

/**
 * DTO for visit patient medication creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientMedicationRequest {
    @NotNull(message = "Visit ID is required")
    private Long visitId;

    @NotBlank(message = "Medication name is required")
    private String medicationName;

    private String dosage;
    private String frequency;
    private String route;
    private Date startDate;
    private Date endDate;
    private Long prescribedById;

}
