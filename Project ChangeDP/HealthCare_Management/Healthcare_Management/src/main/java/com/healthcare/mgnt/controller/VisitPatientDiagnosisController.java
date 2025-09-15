package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.VisitPatientDiagnosisRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientDiagnosisResponseDTO;
import com.healthcare.mgnt.service.VisitPatientDiagnosisService;
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
    public ResponseEntity<Page<VisitPatientDiagnosisResponseDTO>> getAllVisitPatientDiagnoses(@RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient diagnoses, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<VisitPatientDiagnosisResponseDTO> diagnoses = visitPatientDiagnosisService.getAllVisitPatientDiagnoses(pageable);
        return ResponseEntity.ok(diagnoses);
    }

    /**
     * Get visit patient diagnosis by ID.
     */
    @Operation(summary = "Get visit patient diagnosis by ID", description = "Returns a visit patient diagnosis by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DIAGNOSIS_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitPatientDiagnosisResponseDTO> getVisitPatientDiagnosisById(@PathVariable Long id) {
        logger.info("Fetching visit patient diagnosis by id: {}", id);
        VisitPatientDiagnosisResponseDTO diagnosis = visitPatientDiagnosisService.getVisitPatientDiagnosisById(id);
        return ResponseEntity.ok(diagnosis);
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
    public ResponseEntity<VisitPatientDiagnosisResponseDTO> createVisitPatientDiagnosis(@Validated @RequestBody VisitPatientDiagnosisRequestDTO requestDTO) {
        logger.info("Creating new visit patient diagnosis for visitId: {}", requestDTO.getVisitId());
        VisitPatientDiagnosisResponseDTO created = visitPatientDiagnosisService.createVisitPatientDiagnosis(requestDTO);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing visit patient diagnosis.
     */
    @Operation(summary = "Update visit patient diagnosis", description = "Updates an existing visit patient diagnosis")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DIAGNOSIS_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitPatientDiagnosisResponseDTO> updateVisitPatientDiagnosis(@PathVariable Long id, @Validated @RequestBody VisitPatientDiagnosisRequestDTO requestDTO) {
        logger.info("Updating visit patient diagnosis id: {}", id);
        VisitPatientDiagnosisResponseDTO updated = visitPatientDiagnosisService.updateVisitPatientDiagnosis(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a visit patient diagnosis by ID.
     */
    @Operation(summary = "Delete visit patient diagnosis", description = "Deletes a visit patient diagnosis by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DIAGNOSIS_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitPatientDiagnosis(@PathVariable Long id) {
        logger.info("Deleting visit patient diagnosis id: {}", id);
        visitPatientDiagnosisService.deleteVisitPatientDiagnosis(id);
        return ResponseEntity.noContent().build();
    }
}
