package com.healthcare.mgnt.repository.user;

import com.healthcare.mgnt.entity.user.RolePermissionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RolePermissionMap entity.
 * Provides CRUD operations and custom queries for role-permission mappings in the HealthCare Management System.
 */
@Repository
public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
