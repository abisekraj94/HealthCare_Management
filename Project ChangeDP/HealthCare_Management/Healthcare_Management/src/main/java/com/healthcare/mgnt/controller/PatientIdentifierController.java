package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.PatientIdentifierRequest;
import com.healthcare.mgnt.dto.PatientIdentifierResponse;
import com.healthcare.mgnt.service.Implementation.PatientIdentifierService;
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
 * Controller for patient identifier management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patient-identifiers")
public class PatientIdentifierController {
    private static final Logger logger = LoggerFactory.getLogger(PatientIdentifierController.class);

    @Autowired
    private PatientIdentifierService patientIdentifierService;

    /**
     * Get all patient identifiers (paginated).
     */
    @Operation(summary = "Get all patient identifiers", description = "Returns a paginated list of patient identifiers")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<PatientIdentifierResponse>> getAllPatientIdentifiers(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient identifiers, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PatientIdentifierResponse> identifiers = patientIdentifierService.getAllPatientIdentifiers(pageable);
            return ResponseEntity.ok(identifiers);
        } catch (Exception ex) {
            logger.error("Error fetching patient identifiers: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get patient identifier by ID.
     */
    @Operation(summary = "Get patient identifier by ID", description = "Returns a patient identifier by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientIdentifierResponse> getPatientIdentifierById(@PathVariable Long id) {
        logger.info("Fetching patient identifier by id: {}", id);
        try {
            PatientIdentifierResponse identifier = patientIdentifierService.getPatientIdentifierById(id);
            if (identifier == null) {
                logger.warn("Patient identifier not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(identifier);
        } catch (Exception ex) {
            logger.error("Error fetching patient identifier by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new patient identifier.
     */
    @Operation(summary = "Create a new patient identifier", description = "Creates a new patient identifier")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<PatientIdentifierResponse> createPatientIdentifier(@Validated @RequestBody PatientIdentifierRequest requestDTO) {
        logger.info("Creating new patient identifier for patientId: {}", requestDTO.getPatientId());
        try {
            PatientIdentifierResponse created = patientIdentifierService.createPatientIdentifier(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating patient identifier: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing patient identifier.
     */
    @Operation(summary = "Update patient identifier", description = "Updates an existing patient identifier")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientIdentifierResponse> updatePatientIdentifier(@PathVariable Long id, @Validated @RequestBody PatientIdentifierRequest requestDTO) {
        logger.info("Updating patient identifier id: {}", id);
        try {
            PatientIdentifierResponse updated = patientIdentifierService.updatePatientIdentifier(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient identifier id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a patient identifier by ID.
     */
    @Operation(summary = "Delete patient identifier", description = "Deletes a patient identifier by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientIdentifier(@PathVariable Long id) {
        logger.info("Deleting patient identifier id: {}", id);
        try {
            patientIdentifierService.deletePatientIdentifier(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting patient identifier id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
