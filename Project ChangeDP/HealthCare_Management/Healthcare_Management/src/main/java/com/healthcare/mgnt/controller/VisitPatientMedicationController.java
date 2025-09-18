package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequest;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponse;
import com.healthcare.mgnt.service.Implementation.VisitPatientMedicationService;
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
 * Controller for visit patient medication management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/visit-patient-medications")
public class VisitPatientMedicationController {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientMedicationController.class);

    @Autowired
    private VisitPatientMedicationService visitPatientMedicationService;

    /**
     * Get all visit patient medications (paginated).
     */
    @Operation(summary = "Get all visit patient medications", description = "Returns a paginated list of visit patient medications")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<VisitPatientMedicationResponse>> getAllVisitPatientMedications(@RequestParam(defaultValue = "0") int page,
                                                                                              @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient medications, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<VisitPatientMedicationResponse> medications = visitPatientMedicationService.getAllVisitPatientMedications(pageable);
            return ResponseEntity.ok(medications);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient medications: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get visit patient medication by ID.
     */
    @Operation(summary = "Get visit patient medication by ID", description = "Returns a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitPatientMedicationResponse> getVisitPatientMedicationById(@PathVariable Long id) {
        logger.info("Fetching visit patient medication by id: {}", id);
        try {
            VisitPatientMedicationResponse medication = visitPatientMedicationService.getVisitPatientMedicationById(id);
            if (medication == null) {
                logger.warn("Visit patient medication not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(medication);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient medication by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new visit patient medication.
     */
    @Operation(summary = "Create a new visit patient medication", description = "Creates a new visit patient medication")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<VisitPatientMedicationResponse> createVisitPatientMedication(@Validated @RequestBody VisitPatientMedicationRequest requestDTO) {
        logger.info("Creating new visit patient medication for visitId: {}", requestDTO.getVisitId());
        try {
            VisitPatientMedicationResponse created = visitPatientMedicationService.createVisitPatientMedication(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating visit patient medication: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing visit patient medication.
     */
    @Operation(summary = "Update visit patient medication", description = "Updates an existing visit patient medication")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitPatientMedicationResponse> updateVisitPatientMedication(@PathVariable Long id, @Validated @RequestBody VisitPatientMedicationRequest requestDTO) {
        logger.info("Updating visit patient medication id: {}", id);
        try {
            VisitPatientMedicationResponse updated = visitPatientMedicationService.updateVisitPatientMedication(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating visit patient medication id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a visit patient medication by ID.
     */
    @Operation(summary = "Delete visit patient medication", description = "Deletes a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitPatientMedication(@PathVariable Long id) {
        logger.info("Deleting visit patient medication id: {}", id);
        try {
            visitPatientMedicationService.deleteVisitPatientMedication(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting visit patient medication id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
