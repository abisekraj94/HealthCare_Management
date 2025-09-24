package com.healthcare.mgnt.entity.patient;

import com.healthcare.mgnt.entity.audit.Auditable;
import com.healthcare.mgnt.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import java.sql.Date;

/**
 * Entity representing a medication prescribed during a patient's visit in the HealthCare Management System.
 * Stores details about the medication, dosage, frequency, route, prescription dates, and prescriber.
 */
@Entity
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_patient_medication")
public class VisitPatientMedication extends Auditable {
    /** Unique identifier for the medication record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private Long medicationId;

    /** The patient visit during which the medication was prescribed. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    /** Name of the prescribed medication. */
    @Column(name = "medication_name", length = 100)
    private String medicationName;

    /** Dosage of the medication. */
    @Column(name = "dosage", length = 50)
    private String dosage;

    /** Frequency of medication administration. */
    @Column(name = "frequency", length = 50)
    private String frequency;

    /** Route of administration (e.g., oral, IV). */
    @Column(name = "route", length = 50)
    private String route;

    /** Date when the medication was started. */
    @Column(name = "start_date")
    private Date startDate;

    /** Date when the medication was ended, if applicable. */
    @Column(name = "end_date")
    private Date endDate;

    /** User who prescribed the medication. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescribed_by")
    private User prescribedBy;

}
