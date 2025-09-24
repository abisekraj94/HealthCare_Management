package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * DTO for visit patient diagnosis response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientDiagnosisResponse {
    private Long diagnosisId;
    private Long visitId;
    private String diagnosisCode;
    private String diagnosisDescription;
    private Long diagnosedById;
    private Timestamp diagnosedAt;

}

