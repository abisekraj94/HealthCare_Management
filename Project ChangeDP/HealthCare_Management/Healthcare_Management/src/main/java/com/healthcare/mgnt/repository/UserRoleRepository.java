package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for UserRole entity.
 * Provides CRUD operations and custom queries for user-role mappings in the HealthCare Management System.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
