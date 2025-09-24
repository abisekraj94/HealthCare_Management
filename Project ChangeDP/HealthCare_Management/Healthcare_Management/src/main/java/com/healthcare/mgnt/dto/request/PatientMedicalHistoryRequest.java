package com.healthcare.mgnt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

/**
 * DTO for patient medical history creation/update requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientMedicalHistoryRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotBlank(message = "Condition is required")
    private String condition;

    private String description;
    private Date diagnosedAt;
    private Date resolvedAt;

}
