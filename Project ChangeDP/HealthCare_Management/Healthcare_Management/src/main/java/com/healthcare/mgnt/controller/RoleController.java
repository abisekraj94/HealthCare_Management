package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.RoleRequest;
import com.healthcare.mgnt.dto.RoleResponse;
import com.healthcare.mgnt.service.Implementation.RoleService;
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
 * Controller for role management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * Get all roles (paginated).
     */
    @Operation(summary = "Get all roles", description = "Returns a paginated list of roles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getAllRoles(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all roles, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RoleResponse> roles = roleService.getAllRoles(pageable);
            return ResponseEntity.ok(roles);
        } catch (Exception ex) {
            logger.error("Error fetching roles: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get role by ID.
     */
    @Operation(summary = "Get role by ID", description = "Returns a role by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        logger.info("Fetching role by id: {}", id);
        try {
            RoleResponse role = roleService.getRoleById(id);
            if (role == null) {
                logger.warn("Role not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(role);
        } catch (Exception ex) {
            logger.error("Error fetching role by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new role.
     */
    @Operation(summary = "Create a new role", description = "Creates a new role")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Role created"),
        @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Validated @RequestBody RoleRequest requestDTO) {
        logger.info("Creating new role: {}", requestDTO.getName());
        try {
            RoleResponse created = roleService.createRole(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating role: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing role.
     */
    @Operation(summary = "Update role", description = "Updates an existing role")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role updated"),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Validated @RequestBody RoleRequest requestDTO) {
        logger.info("Updating role id: {}", id);
        try {
            RoleResponse updated = roleService.updateRole(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating role id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a role by ID.
     */
    @Operation(summary = "Delete role", description = "Deletes a role by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Role deleted"),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ROLE_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        logger.info("Deleting role id: {}", id);
        try {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting role id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
