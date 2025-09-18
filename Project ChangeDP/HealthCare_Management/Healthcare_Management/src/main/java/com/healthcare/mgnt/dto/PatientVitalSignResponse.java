package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * DTO for patient vital sign response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientVitalSignResponse {
    private Long vitalSignId;
    private Long patientId;
    private Timestamp recordedAt;
    private Double temperature;
    private String bloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private String notes;

}

