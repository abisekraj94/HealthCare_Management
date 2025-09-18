package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.RolePermissionRequest;
import com.healthcare.mgnt.dto.RolePermissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing role permissions, defining access rights for different user roles.
 * Provides CRUD operations and paginated retrieval of role permission records.
 */
public interface IRolePermissionService {
    /**
     * Retrieves a paginated list of all role permissions.
     * @param pageable pagination information
     * @return paginated list of role permission response DTOs
     */
    Page<RolePermissionResponse> getAllRolePermissions(Pageable pageable);

    /**
     * Retrieves a role permission by its unique ID.
     * @param id role permission ID
     * @return role permission response DTO
     */
    RolePermissionResponse getRolePermissionById(Long id);

    /**
     * Creates a new role permission record.
     * @param dto role permission request DTO
     * @return created role permission response DTO
     */
    RolePermissionResponse createRolePermission(RolePermissionRequest dto);

    /**
     * Updates an existing role permission record.
     * @param id role permission ID
     * @param dto role permission request DTO with updated details
     * @return updated role permission response DTO
     */
    RolePermissionResponse updateRolePermission(Long id, RolePermissionRequest dto);

    /**
     * Deletes a role permission by its unique ID.
     * @param id role permission ID
     */
    void deleteRolePermission(Long id);
}
