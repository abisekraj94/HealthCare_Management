package com.healthcare.mgnt.service.Implementation.user;

import com.healthcare.mgnt.dto.request.RoleRequest;
import com.healthcare.mgnt.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing user roles within the healthcare system (e.g., doctor, nurse, admin).
 * Provides CRUD operations and paginated retrieval of role records.
 */
public interface IRoleService {
    /**
     * Retrieves a paginated list of all roles.
     * @param pageable pagination information
     * @return paginated list of role response DTOs
     */
    Page<RoleResponse> getAllRoles(Pageable pageable);

    /**
     * Retrieves a role by its unique ID.
     * @param id role ID
     * @return role response DTO
     */
    RoleResponse getRoleById(Long id);

    /**
     * Creates a new role record.
     * @param dto role request DTO
     * @return created role response DTO
     */
    RoleResponse createRole(RoleRequest dto);

    /**
     * Updates an existing role record.
     * @param id role ID
     * @param dto role request DTO with updated details
     * @return updated role response DTO
     */
    RoleResponse updateRole(Long id, RoleRequest dto);

    /**
     * Deletes a role by its unique ID.
     * @param id role ID
     */
    void deleteRole(Long id);
}
