package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_patient_allergy")
public class VisitPatientAllergy extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergy_id")
    private Long allergyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    @Column(name = "allergy_name", length = 100)
    private String allergyName;

    @Column(name = "reaction", length = 100)
    private String reaction;

    @Column(name = "severity", length = 50)
    private String severity;

    @Column(name = "notes")
    private String notes;
}

