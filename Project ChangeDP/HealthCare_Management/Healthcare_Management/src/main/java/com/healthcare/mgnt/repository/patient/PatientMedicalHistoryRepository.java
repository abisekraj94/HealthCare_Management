package com.healthcare.mgnt.repository.patient;

import com.healthcare.mgnt.entity.patient.PatientMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for PatientMedicalHistory entity.
 * Provides CRUD operations and custom queries for patient medical history in the HealthCare Management System.
 */
@Repository
public interface PatientMedicalHistoryRepository extends JpaRepository<PatientMedicalHistory, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
