package com.healthcare.mgnt.controller.user;

import com.healthcare.mgnt.dto.request.RolePermissionRequest;
import com.healthcare.mgnt.dto.response.RolePermissionResponse;
import com.healthcare.mgnt.service.Implementation.user.RolePermissionService;
import com.healthcare.mgnt.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * Controller for role permission management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/permissions")
public class RolePermissionController {
    private static final Logger logger = LoggerFactory.getLogger(RolePermissionController.class);

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * Get all role permissions (paginated).
     */
    @Operation(summary = "Get all role permissions", description = "Returns a paginated list of role permissions")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public Page<RolePermissionResponse> getAllRolePermissions(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all role permissions, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return rolePermissionService.getAllRolePermissions(pageable);
    }

    /**
     * Get role permission by ID.
     */
    @Operation(summary = "Get role permission by ID", description = "Returns a role permission by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_PERMISSION_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public RolePermissionResponse getRolePermissionById(@PathVariable Long id) {
        logger.info("Fetching role permission by id: {}", id);
            RolePermissionResponse permission = rolePermissionService.getRolePermissionById(id);
            if (permission == null) {
                logger.error("Role permission not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role permission not found");
            }
            return permission;
    }

    /**
     * Create a new role permission.
     */
    @Operation(summary = "Create a new role permission", description = "Creates a new role permission")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public RolePermissionResponse createRolePermission(@Validated @RequestBody RolePermissionRequest requestDTO) {
        logger.info("Creating new role permission with name: {}", requestDTO.getName());
            return rolePermissionService.createRolePermission(requestDTO);
    }

    /**
     * Update an existing role permission.
     */
    @Operation(summary = "Update role permission", description = "Updates an existing role permission")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_PERMISSION_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public RolePermissionResponse updateRolePermission(@PathVariable Long id, @Validated @RequestBody RolePermissionRequest requestDTO) {
        logger.info("Updating role permission id: {}", id);
            return rolePermissionService.updateRolePermission(id, requestDTO);
    }

    /**
     * Delete a role permission by ID.
     */
    @Operation(summary = "Delete role permission", description = "Deletes a role permission by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_PERMISSION_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public void deleteRolePermission(@PathVariable Long id) {
        logger.info("Deleting role permission id: {}", id);
            rolePermissionService.deleteRolePermission(id);
    }
}
