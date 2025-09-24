package com.healthcare.mgnt.repository.patient;

import com.healthcare.mgnt.entity.patient.PatientIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for PatientIdentifier entity.
 * Provides CRUD operations and custom queries for patient identifiers in the HealthCare Management System.
 */
@Repository
public interface PatientIdentifierRepository extends JpaRepository<PatientIdentifier, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
