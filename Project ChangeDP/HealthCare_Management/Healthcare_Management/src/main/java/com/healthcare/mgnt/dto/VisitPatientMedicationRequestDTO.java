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
public class VisitPatientMedicationRequestDTO {
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

    public Long getVisitId() { return visitId; }
    public void setVisitId(Long visitId) { this.visitId = visitId; }
    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public java.sql.Date getStartDate() { return startDate; }
    public void setStartDate(java.sql.Date startDate) { this.startDate = startDate; }
    public java.sql.Date getEndDate() { return endDate; }
    public void setEndDate(java.sql.Date endDate) { this.endDate = endDate; }
    public Long getPrescribedById() { return prescribedById; }
    public void setPrescribedById(Long prescribedById) { this.prescribedById = prescribedById; }
}
