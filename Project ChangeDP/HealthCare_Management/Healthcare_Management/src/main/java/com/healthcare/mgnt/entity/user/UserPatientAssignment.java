package com.healthcare.mgnt.entity.user;

import com.healthcare.mgnt.entity.patient.Patient;
import com.healthcare.mgnt.entity.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity representing the assignment of a user to a patient in the HealthCare Management System.
 * Tracks which user is assigned to which patient, the type of assignment, and assignment dates.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_patient_assignment")
public class UserPatientAssignment extends Auditable {
    /** Unique identifier for the user-patient assignment record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    /** The user assigned to the patient. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The patient to whom the user is assigned. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /** Type of assignment (e.g., primary, consulting). */
    @Column(name = "assignment_type", length = 50)
    private String assignmentType;

    /** Timestamp when the assignment was made. */
    @Column(name = "assigned_at")
    private Timestamp assignedAt;

    /** Timestamp when the assignment was ended, if applicable. */
    @Column(name = "unassigned_at")
    private Timestamp unassignedAt;

}
