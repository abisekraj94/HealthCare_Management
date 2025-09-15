package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.RoleRequestDTO;
import com.healthcare.mgnt.dto.RoleResponseDTO;
import com.healthcare.mgnt.service.RoleService;
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
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> getAllRoles(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all roles, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleResponseDTO> roles = roleService.getAllRoles(pageable);
        return ResponseEntity.ok(roles);
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
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        logger.info("Fetching role by id: {}", id);
        RoleResponseDTO role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
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
    public ResponseEntity<RoleResponseDTO> createRole(@Validated @RequestBody RoleRequestDTO requestDTO) {
        logger.info("Creating new role: {}", requestDTO.getName());
        RoleResponseDTO created = roleService.createRole(requestDTO);
        return ResponseEntity.status(201).body(created);
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
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id, @Validated @RequestBody RoleRequestDTO requestDTO) {
        logger.info("Updating role id: {}", id);
        RoleResponseDTO updated = roleService.updateRole(id, requestDTO);
        return ResponseEntity.ok(updated);
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
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
