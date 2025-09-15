package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_visit")
public class PatientVisit extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long visitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "visit_date", nullable = false)
    private Timestamp visitDate;

    @Column(name = "visit_type", length = 50)
    private String visitType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id")
    private User physician;

    @Column(name = "notes")
    private String notes;
}
