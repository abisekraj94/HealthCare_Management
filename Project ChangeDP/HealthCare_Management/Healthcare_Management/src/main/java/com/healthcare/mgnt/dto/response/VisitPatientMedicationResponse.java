package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * DTO for visit patient medication response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitPatientMedicationResponse {
    private Long medicationId;
    private Long visitId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String route;
    private Date startDate;
    private Date endDate;
    private Long prescribedById;
}
