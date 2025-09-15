package com.healthcare.mgnt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_medical_history")
public class PatientMedicalHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "condition", nullable = false, length = 100)
    private String condition;

    @Column(name = "description")
    private String description;

    @Column(name = "diagnosed_at")
    private Date diagnosedAt;

    @Column(name = "resolved_at")
    private Date resolvedAt;

}

