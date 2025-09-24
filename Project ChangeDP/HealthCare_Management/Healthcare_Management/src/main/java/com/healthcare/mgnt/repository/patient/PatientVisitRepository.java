package com.healthcare.mgnt.repository.patient;

import com.healthcare.mgnt.entity.patient.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for PatientVisit entity.
 * Provides CRUD operations and custom queries for patient visits in the HealthCare Management System.
 */
@Repository
public interface PatientVisitRepository extends JpaRepository<PatientVisit, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
