package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.VisitPatientDiagnosisRequest;
import com.healthcare.mgnt.dto.VisitPatientDiagnosisResponse;
import com.healthcare.mgnt.service.Implementation.VisitPatientDiagnosisService;
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
 * Controller for visit patient diagnosis management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/visit-patient-diagnoses")
public class VisitPatientDiagnosisController {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientDiagnosisController.class);

    @Autowired
    private VisitPatientDiagnosisService visitPatientDiagnosisService;

    /**
     * Get all visit patient diagnoses (paginated).
     */
    @Operation(summary = "Get all visit patient diagnoses", description = "Returns a paginated list of visit patient diagnoses")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<VisitPatientDiagnosisResponse>> getAllVisitPatientDiagnoses(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient diagnoses, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<VisitPatientDiagnosisResponse> diagnoses = visitPatientDiagnosisService.getAllVisitPatientDiagnoses(pageable);
            return ResponseEntity.ok(diagnoses);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient diagnoses: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get visit patient diagnosis by ID.
     */
    @Operation(summary = "Get visit patient diagnosis by ID", description = "Returns a visit patient diagnosis by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_DIAGNOSIS_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitPatientDiagnosisResponse> getVisitPatientDiagnosisById(@PathVariable Long id) {
        logger.info("Fetching visit patient diagnosis by id: {}", id);
        try {
            VisitPatientDiagnosisResponse diagnosis = visitPatientDiagnosisService.getVisitPatientDiagnosisById(id);
            if (diagnosis == null) {
                logger.warn("Visit patient diagnosis not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(diagnosis);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient diagnosis by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new visit patient diagnosis.
     */
    @Operation(summary = "Create a new visit patient diagnosis", description = "Creates a new visit patient diagnosis")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<VisitPatientDiagnosisResponse> createVisitPatientDiagnosis(@Validated @RequestBody VisitPatientDiagnosisRequest requestDTO) {
        logger.info("Creating new visit patient diagnosis for visitId: {}", requestDTO.getVisitId());
        try {
            VisitPatientDiagnosisResponse created = visitPatientDiagnosisService.createVisitPatientDiagnosis(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating visit patient diagnosis: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing visit patient diagnosis.
     */
    @Operation(summary = "Update visit patient diagnosis", description = "Updates an existing visit patient diagnosis")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_DIAGNOSIS_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitPatientDiagnosisResponse> updateVisitPatientDiagnosis(@PathVariable Long id, @Validated @RequestBody VisitPatientDiagnosisRequest requestDTO) {
        logger.info("Updating visit patient diagnosis id: {}", id);
        try {
            VisitPatientDiagnosisResponse updated = visitPatientDiagnosisService.updateVisitPatientDiagnosis(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating visit patient diagnosis id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a visit patient diagnosis by ID.
     */
    @Operation(summary = "Delete visit patient diagnosis", description = "Deletes a visit patient diagnosis by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_DIAGNOSIS_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitPatientDiagnosis(@PathVariable Long id) {
        logger.info("Deleting visit patient diagnosis id: {}", id);
        try {
            visitPatientDiagnosisService.deleteVisitPatientDiagnosis(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting visit patient diagnosis id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
