package com.healthcare.mgnt.entity.patient;

import com.healthcare.mgnt.entity.audit.Auditable;
import com.healthcare.mgnt.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity representing a patient's visit in the HealthCare Management System.
 * Stores details about the visit, including date, type, physician, and notes.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_visit")
public class PatientVisit extends Auditable {
    /** Unique identifier for the patient visit. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long visitId;

    /** The patient associated with this visit. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /** Date and time of the visit. */
    @Column(name = "visit_date", nullable = false)
    private Timestamp visitDate;

    /** Type of visit (e.g., consultation, follow-up). */
    @Column(name = "visit_type", length = 50)
    private String visitType;

    /** Physician who attended the visit. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id")
    private User physician;

    /** Additional notes about the visit. */
    @Column(name = "notes")
    private String notes;
}
