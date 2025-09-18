package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.RolePermissionRequest;
import com.healthcare.mgnt.dto.RolePermissionResponse;
import com.healthcare.mgnt.service.Implementation.RolePermissionService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<RolePermissionResponse>> getAllRolePermissions(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all role permissions, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RolePermissionResponse> permissions = rolePermissionService.getAllRolePermissions(pageable);
            return ResponseEntity.ok(permissions);
        } catch (Exception ex) {
            logger.error("Error fetching role permissions: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<RolePermissionResponse> getRolePermissionById(@PathVariable Long id) {
        logger.info("Fetching role permission by id: {}", id);
        try {
            RolePermissionResponse permission = rolePermissionService.getRolePermissionById(id);
            if (permission == null) {
                logger.warn("Role permission not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(permission);
        } catch (Exception ex) {
            logger.error("Error fetching role permission by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<RolePermissionResponse> createRolePermission(@Validated @RequestBody RolePermissionRequest requestDTO) {
        logger.info("Creating new role permission with name: {}", requestDTO.getName());
        try {
            RolePermissionResponse created = rolePermissionService.createRolePermission(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating role permission: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<RolePermissionResponse> updateRolePermission(@PathVariable Long id, @Validated @RequestBody RolePermissionRequest requestDTO) {
        logger.info("Updating role permission id: {}", id);
        try {
            RolePermissionResponse updated = rolePermissionService.updateRolePermission(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating role permission id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        logger.info("Deleting role permission id: {}", id);
        try {
            rolePermissionService.deleteRolePermission(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting role permission id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
