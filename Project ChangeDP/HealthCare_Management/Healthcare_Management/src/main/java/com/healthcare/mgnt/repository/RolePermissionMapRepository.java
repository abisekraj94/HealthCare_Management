package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.RolePermissionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, Long> {
}

