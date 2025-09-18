package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a unique identifier for a patient (e.g., insurance number, government ID).
 * Associates an identifier type and value with a patient in the HealthCare Management System.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_identifier")
public class PatientIdentifier extends Auditable {
    /** Unique identifier for the patient identifier record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identifier_id")
    private Long identifierId;

    /** The patient to whom this identifier belongs. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /** Type of identifier (e.g., SSN, insurance, passport). */
    @Column(name = "identifier_type", nullable = false, length = 50)
    private String identifierType;

    /** Value of the identifier. */
    @Column(name = "identifier_value", nullable = false, length = 100)
    private String identifierValue;

}
