package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an allergy recorded during a patient's visit in the HealthCare Management System.
 * Stores details about the allergy, reaction, severity, and additional notes for the visit.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_patient_allergy")
public class VisitPatientAllergy extends Auditable {
    /** Unique identifier for the allergy record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergy_id")
    private Long allergyId;

    /** The patient visit during which the allergy was recorded. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    /** Name of the allergy. */
    @Column(name = "allergy_name", length = 100)
    private String allergyName;

    /** Description of the reaction to the allergy. */
    @Column(name = "reaction", length = 100)
    private String reaction;

    /** Severity of the allergy reaction. */
    @Column(name = "severity", length = 50)
    private String severity;

    /** Additional notes about the allergy. */
    @Column(name = "notes")
    private String notes;
}
