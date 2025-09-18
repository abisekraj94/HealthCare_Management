package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.VisitPatientDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for VisitPatientDiagnosis entity.
 * Provides CRUD operations and custom queries for patient visit diagnoses in the HealthCare Management System.
 */
@Repository
public interface VisitPatientDiagnosisRepository extends JpaRepository<VisitPatientDiagnosis, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
