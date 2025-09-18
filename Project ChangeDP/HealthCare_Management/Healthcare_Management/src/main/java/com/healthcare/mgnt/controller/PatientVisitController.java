package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.PatientVisitRequest;
import com.healthcare.mgnt.dto.PatientVisitResponse;
import com.healthcare.mgnt.service.Implementation.PatientVisitService;
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
 * Controller for patient visit management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patient-visits")
public class PatientVisitController {
    private static final Logger logger = LoggerFactory.getLogger(PatientVisitController.class);

    @Autowired
    private PatientVisitService patientVisitService;

    /**
     * Get all patient visits (paginated).
     */
    @Operation(summary = "Get all patient visits", description = "Returns a paginated list of patient visits")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<PatientVisitResponse>> getAllPatientVisits(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient visits, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PatientVisitResponse> visits = patientVisitService.getAllPatientVisits(pageable);
            return ResponseEntity.ok(visits);
        } catch (Exception ex) {
            logger.error("Error fetching patient visits: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get patient visit by ID.
     */
    @Operation(summary = "Get patient visit by ID", description = "Returns a patient visit by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientVisitResponse> getPatientVisitById(@PathVariable Long id) {
        logger.info("Fetching patient visit by id: {}", id);
        try {
            PatientVisitResponse visit = patientVisitService.getPatientVisitById(id);
            if (visit == null) {
                logger.warn("Patient visit not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(visit);
        } catch (Exception ex) {
            logger.error("Error fetching patient visit by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new patient visit.
     */
    @Operation(summary = "Create a new patient visit", description = "Creates a new patient visit")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<PatientVisitResponse> createPatientVisit(@Validated @RequestBody PatientVisitRequest requestDTO) {
        logger.info("Creating new patient visit for patientId: {}", requestDTO.getPatientId());
        try {
            PatientVisitResponse created = patientVisitService.createPatientVisit(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating patient visit: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing patient visit.
     */
    @Operation(summary = "Update patient visit", description = "Updates an existing patient visit")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientVisitResponse> updatePatientVisit(@PathVariable Long id, @Validated @RequestBody PatientVisitRequest requestDTO) {
        logger.info("Updating patient visit id: {}", id);
        try {
            PatientVisitResponse updated = patientVisitService.updatePatientVisit(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient visit id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a patient visit by ID.
     */
    @Operation(summary = "Delete patient visit", description = "Deletes a patient visit by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientVisit(@PathVariable Long id) {
        logger.info("Deleting patient visit id: {}", id);
        try {
            patientVisitService.deletePatientVisit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting patient visit id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
