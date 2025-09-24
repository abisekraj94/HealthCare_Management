package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * DTO for patient medical history response data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientMedicalHistoryResponse {
    private Long historyId;
    private Long patientId;
    private String condition;
    private String description;
    private Date diagnosedAt;
    private Date resolvedAt;

}

