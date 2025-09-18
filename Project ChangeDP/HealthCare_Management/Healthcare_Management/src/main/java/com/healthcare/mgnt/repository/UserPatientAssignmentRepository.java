package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.UserPatientAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for UserPatientAssignment entity.
 * Provides CRUD operations and custom queries for user-patient assignments in the HealthCare Management System.
 */
@Repository
public interface UserPatientAssignmentRepository extends JpaRepository<UserPatientAssignment, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
