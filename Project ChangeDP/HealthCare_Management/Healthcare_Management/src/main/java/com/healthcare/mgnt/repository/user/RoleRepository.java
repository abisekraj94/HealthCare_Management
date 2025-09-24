package com.healthcare.mgnt.repository.user;

import com.healthcare.mgnt.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Role entity.
 * Provides CRUD operations and custom queries for roles in the HealthCare Management System.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
