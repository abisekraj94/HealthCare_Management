package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.UserPatientAssignmentRequestDTO;
import com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO;
import com.healthcare.mgnt.service.UserPatientAssignmentService;
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
 * Controller for user-patient assignment management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/user-patient-assignments")
public class UserPatientAssignmentController {
    private static final Logger logger = LoggerFactory.getLogger(UserPatientAssignmentController.class);

    @Autowired
    private UserPatientAssignmentService userPatientAssignmentService;

    /**
     * Get all user-patient assignments (paginated).
     */
    @Operation(summary = "Get all user-patient assignments", description = "Returns a paginated list of user-patient assignments")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<UserPatientAssignmentResponseDTO>> getAllUserPatientAssignments(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all user-patient assignments, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserPatientAssignmentResponseDTO> assignments = userPatientAssignmentService.getAllUserPatientAssignments(pageable);
        return ResponseEntity.ok(assignments);
    }

    /**
     * Get user-patient assignment by ID.
     */
    @Operation(summary = "Get user-patient assignment by ID", description = "Returns a user-patient assignment by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ASSIGNMENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserPatientAssignmentResponseDTO> getUserPatientAssignmentById(@PathVariable Long id) {
        logger.info("Fetching user-patient assignment by id: {}", id);
        UserPatientAssignmentResponseDTO assignment = userPatientAssignmentService.getUserPatientAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    /**
     * Create a new user-patient assignment.
     */
    @Operation(summary = "Create a new user-patient assignment", description = "Creates a new user-patient assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<UserPatientAssignmentResponseDTO> createUserPatientAssignment(@Validated @RequestBody UserPatientAssignmentRequestDTO requestDTO) {
        logger.info("Creating new user-patient assignment for userId: {}, patientId: {}", requestDTO.getUserId(), requestDTO.getPatientId());
        UserPatientAssignmentResponseDTO created = userPatientAssignmentService.createUserPatientAssignment(requestDTO);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing user-patient assignment.
     */
    @Operation(summary = "Update user-patient assignment", description = "Updates an existing user-patient assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ASSIGNMENT_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserPatientAssignmentResponseDTO> updateUserPatientAssignment(@PathVariable Long id, @Validated @RequestBody UserPatientAssignmentRequestDTO requestDTO) {
        logger.info("Updating user-patient assignment id: {}", id);
        UserPatientAssignmentResponseDTO updated = userPatientAssignmentService.updateUserPatientAssignment(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a user-patient assignment by ID.
     */
    @Operation(summary = "Delete user-patient assignment", description = "Deletes a user-patient assignment by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.ASSIGNMENT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPatientAssignment(@PathVariable Long id) {
        logger.info("Deleting user-patient assignment id: {}", id);
        userPatientAssignmentService.deleteUserPatientAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
