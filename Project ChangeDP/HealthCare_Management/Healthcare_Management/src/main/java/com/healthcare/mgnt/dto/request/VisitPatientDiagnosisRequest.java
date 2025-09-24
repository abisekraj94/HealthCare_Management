package com.healthcare.mgnt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * DTO for visit patient diagnosis creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientDiagnosisRequest {
    @NotNull(message = "Visit ID is required")
    private Long visitId;

    @NotBlank(message = "Diagnosis code is required")
    private String diagnosisCode;

    private String diagnosisDescription;
    private Long diagnosedById;
    private Timestamp diagnosedAt;

}
