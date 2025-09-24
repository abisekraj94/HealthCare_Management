package com.healthcare.mgnt.entity.patient;

import com.healthcare.mgnt.entity.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.sql.Date;

/**
 * Entity representing a patient's medical history in the HealthCare Management System.
 * Stores information about medical conditions, diagnosis, and resolution dates for a patient.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "patient_medical_history")
public class PatientMedicalHistory extends Auditable {
    /** Unique identifier for the medical history record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    /** The patient to whom this medical history belongs. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /** Name of the medical condition. */
    @Column(name = "condition", nullable = false, length = 100)
    private String condition;

    /** Description or notes about the medical condition. */
    @Column(name = "description")
    private String description;

    /** Date when the condition was diagnosed. */
    @Column(name = "diagnosed_at")
    private Date diagnosedAt;

    /** Date when the condition was resolved, if applicable. */
    @Column(name = "resolved_at")
    private Date resolvedAt;

}
