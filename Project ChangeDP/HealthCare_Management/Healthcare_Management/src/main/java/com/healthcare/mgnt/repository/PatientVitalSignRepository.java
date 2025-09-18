package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.PatientVitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for PatientVitalSign entity.
 * Provides CRUD operations and custom queries for patient vital signs in the HealthCare Management System.
 */
@Repository
public interface PatientVitalSignRepository extends JpaRepository<PatientVitalSign, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
