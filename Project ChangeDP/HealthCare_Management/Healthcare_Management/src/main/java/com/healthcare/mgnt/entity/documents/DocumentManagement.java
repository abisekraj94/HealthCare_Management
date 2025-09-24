package com.healthcare.mgnt.entity.documents;

import com.healthcare.mgnt.entity.audit.Auditable;
import com.healthcare.mgnt.entity.patient.Patient;
import com.healthcare.mgnt.entity.patient.PatientVisit;
import com.healthcare.mgnt.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.sql.Timestamp;

/**
 * Entity representing a document uploaded for a patient or visit in the HealthCare Management System.
 * Stores metadata about the document, including file details, uploader, and associations to patient and visit.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "document_management")
public class DocumentManagement extends Auditable {
    /** Unique identifier for the document. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    /** The patient associated with this document. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    /** The visit associated with this document. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private PatientVisit visit;

    /** Name of the uploaded file. */
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    /** Type of the uploaded file (e.g., PDF, JPG). */
    @Column(name = "file_type", length = 50)
    private String fileType;

    /** URL or path to the uploaded file. */
    @Column(name = "file_url", nullable = false, length = 255)
    private String fileUrl;

    /** The user who uploaded the document. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    /** Timestamp when the document was uploaded. */
    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;

    /** Optional description of the document. */
    @Column(name = "description")
    private String description;

}
