package com.healthcare.mgnt.entity.patient;

import com.healthcare.mgnt.entity.audit.Auditable;
import com.healthcare.mgnt.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;


/**
 * Entity representing a patient in the HealthCare Management System.
 * Stores personal and contact information, as well as physician associations.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient extends Auditable {
    /** Unique identifier for the patient. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    /** Medical Record Number (MRN) for the patient. */
    @Column(name = "mrn", nullable = false, unique = true, length = 50)
    private String mrn;

    /** First name of the patient. */
    @Column(name = "first_name", length = 100)
    private String firstName;

    /** Last name of the patient. */
    @Column(name = "last_name", length = 100)
    private String lastName;

    /** Date of birth of the patient. */
    @Column(name = "dob")
    private Date dob;

    /** Gender of the patient. */
    @Column(name = "gender", length = 20)
    private String gender;

    /** Phone number of the patient. */
    @Column(name = "phone", length = 20)
    private String phone;

    /** Email address of the patient. */
    @Column(name = "email", length = 255)
    private String email;

    /** Address of the patient. */
    @Column(name = "address")
    private String address;

    /** Primary physician assigned to the patient. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_physician_id")
    private User primaryPhysician;

    /** Referral physician for the patient. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referral_physician_id")
    private User referralPhysician;

    // TODO: Soft delete implementation
    @Column(name = "is_active")
    private Boolean isActive;
}
