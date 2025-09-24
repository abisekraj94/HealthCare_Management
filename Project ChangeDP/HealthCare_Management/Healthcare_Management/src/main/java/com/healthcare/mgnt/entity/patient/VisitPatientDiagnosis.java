package com.healthcare.mgnt.entity.patient;

import com.healthcare.mgnt.entity.audit.Auditable;
import com.healthcare.mgnt.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.sql.Timestamp;

/**
 * Entity representing a diagnosis made during a patient's visit in the HealthCare Management System.
 * Stores diagnosis code, description, diagnosing user, and timestamp for the visit.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "visit_patient_diagnosis")
public class VisitPatientDiagnosis extends Auditable {
    /** Unique identifier for the diagnosis record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    /** The patient visit during which the diagnosis was made. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    /** Code representing the diagnosis (e.g., ICD-10 code). */
    @Column(name = "diagnosis_code", length = 50)
    private String diagnosisCode;

    /** Description of the diagnosis. */
    @Column(name = "diagnosis_description")
    private String diagnosisDescription;

    /** User who made the diagnosis. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosed_by")
    private User diagnosedBy;

    /** Timestamp when the diagnosis was made. */
    @Column(name = "diagnosed_at")
    private Timestamp diagnosedAt;

}
