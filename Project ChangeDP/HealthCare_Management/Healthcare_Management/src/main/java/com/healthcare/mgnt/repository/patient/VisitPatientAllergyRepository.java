package com.healthcare.mgnt.repository.patient;

import com.healthcare.mgnt.entity.patient.VisitPatientAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for VisitPatientAllergy entity.
 * Provides CRUD operations and custom queries for patient visit allergies in the HealthCare Management System.
 */
@Repository
public interface VisitPatientAllergyRepository extends JpaRepository<VisitPatientAllergy, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
