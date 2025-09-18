package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity representing a patient's vital sign record in the HealthCare Management System.
 * Stores measurements such as temperature, blood pressure, heart rate, and respiratory rate for a patient.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_vital_sign")
public class PatientVitalSign extends Auditable {
    /** Unique identifier for the vital sign record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vital_sign_id")
    private Long vitalSignId;

    /** The patient to whom this vital sign record belongs. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /** Timestamp when the vital signs were recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Timestamp recordedAt;

    /** Body temperature of the patient. */
    @Column(name = "temperature")
    private Double temperature;

    /** Blood pressure reading of the patient. */
    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    /** Heart rate of the patient. */
    @Column(name = "heart_rate")
    private Integer heartRate;

    /** Respiratory rate of the patient. */
    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;

    /** Additional notes about the vital sign record. */
    @Column(name = "notes")
    private String notes;

}
