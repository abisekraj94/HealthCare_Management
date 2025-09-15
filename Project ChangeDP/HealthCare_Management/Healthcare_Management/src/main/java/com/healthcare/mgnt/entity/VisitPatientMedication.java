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
@Table(name = "visit_patient_medication")
public class VisitPatientMedication extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private Long medicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private PatientVisit visit;

    @Column(name = "medication_name", length = 100)
    private String medicationName;

    @Column(name = "dosage", length = 50)
    private String dosage;

    @Column(name = "frequency", length = 50)
    private String frequency;

    @Column(name = "route", length = 50)
    private String route;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescribed_by")
    private User prescribedBy;


}
