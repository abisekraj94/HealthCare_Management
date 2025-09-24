package com.healthcare.mgnt.controller.patient;

import com.healthcare.mgnt.dto.request.UserPatientAssignmentRequest;
import com.healthcare.mgnt.dto.response.UserPatientAssignmentResponse;
import com.healthcare.mgnt.service.Implementation.user.UserPatientAssignmentService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

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
    public Page<UserPatientAssignmentResponse> getAllUserPatientAssignments(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all user-patient assignments, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return userPatientAssignmentService.getAllUserPatientAssignments(pageable);
    }

    /**
     * Get user-patient assignment by ID.
     */
    @Operation(summary = "Get user-patient assignment by ID", description = "Returns a user-patient assignment by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_PATIENT_ASSIGNMENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public UserPatientAssignmentResponse getUserPatientAssignmentById(@PathVariable Long id) {
        logger.info("Fetching user-patient assignment by id: {}", id);
            UserPatientAssignmentResponse assignment = userPatientAssignmentService.getUserPatientAssignmentById(id);
            if (assignment == null) {
                logger.error("User-patient assignment not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User-patient assignment not found");
            }
            return assignment;
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
    public UserPatientAssignmentResponse createUserPatientAssignment(@Validated @RequestBody UserPatientAssignmentRequest requestDTO) {
        logger.info("Creating new user-patient assignment for userId: {}", requestDTO.getUserId());
        return userPatientAssignmentService.createUserPatientAssignment(requestDTO);
    }

    /**
     * Update an existing user-patient assignment.
     */
    @Operation(summary = "Update user-patient assignment", description = "Updates an existing user-patient assignment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_PATIENT_ASSIGNMENT_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public UserPatientAssignmentResponse updateUserPatientAssignment(@PathVariable Long id, @Validated @RequestBody UserPatientAssignmentRequest requestDTO) {
        logger.info("Updating user-patient assignment id: {}", id);
        return userPatientAssignmentService.updateUserPatientAssignment(id, requestDTO);
    }

    /**
     * Delete a user-patient assignment by ID.
     */
    @Operation(summary = "Delete user-patient assignment", description = "Deletes a user-patient assignment by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_PATIENT_ASSIGNMENT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public void deleteUserPatientAssignment(@PathVariable Long id) {
        logger.info("Deleting user-patient assignment id: {}", id);
        userPatientAssignmentService.deleteUserPatientAssignment(id);
    }
}
