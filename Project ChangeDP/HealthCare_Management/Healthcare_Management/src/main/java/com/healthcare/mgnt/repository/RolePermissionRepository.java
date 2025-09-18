package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RolePermission entity.
 * Provides CRUD operations and custom queries for role permissions in the HealthCare Management System.
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
