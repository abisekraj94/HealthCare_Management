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
@Table(name = "visit_patient_diagnosis")
public class VisitPatientDiagnosis extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    @Column(name = "diagnosis_code", length = 50)
    private String diagnosisCode;

    @Column(name = "diagnosis_description")
    private String diagnosisDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosed_by")
    private User diagnosedBy;

    @Column(name = "diagnosed_at")
    private Timestamp diagnosedAt;

}

