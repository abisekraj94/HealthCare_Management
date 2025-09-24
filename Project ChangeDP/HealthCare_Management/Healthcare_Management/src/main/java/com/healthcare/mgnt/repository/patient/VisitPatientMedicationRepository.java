package com.healthcare.mgnt.repository.patient;

import com.healthcare.mgnt.entity.patient.VisitPatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for VisitPatientMedication entity.
 * Provides CRUD operations and custom queries for patient visit medications in the HealthCare Management System.
 */
@Repository
public interface VisitPatientMedicationRepository extends JpaRepository<VisitPatientMedication, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
