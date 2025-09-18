package com.healthcare.mgnt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientVisitRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Visit date is required")
    private Timestamp visitDate;

    private String visitType;
    private Long physicianId;
    private String notes;

}
