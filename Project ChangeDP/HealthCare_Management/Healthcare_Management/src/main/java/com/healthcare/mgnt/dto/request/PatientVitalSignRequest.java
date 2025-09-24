package com.healthcare.mgnt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * DTO for patient vital sign creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientVitalSignRequest {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Recorded at is required")
    private Timestamp recordedAt;

    private Double temperature;
    private String bloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private String notes;

}
