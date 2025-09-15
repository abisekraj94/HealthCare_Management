package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponseDTO;
import com.healthcare.mgnt.service.VisitPatientMedicationService;
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
    public ResponseEntity<Page<VisitPatientMedicationResponseDTO>> getAllVisitPatientMedications(@RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient medications, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<VisitPatientMedicationResponseDTO> medications = visitPatientMedicationService.getAllVisitPatientMedications(pageable);
        return ResponseEntity.ok(medications);
    }

    /**
     * Get visit patient medication by ID.
     */
    @Operation(summary = "Get visit patient medication by ID", description = "Returns a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.MEDICATION_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitPatientMedicationResponseDTO> getVisitPatientMedicationById(@PathVariable Long id) {
        logger.info("Fetching visit patient medication by id: {}", id);
        VisitPatientMedicationResponseDTO medication = visitPatientMedicationService.getVisitPatientMedicationById(id);
        return ResponseEntity.ok(medication);
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
    public ResponseEntity<VisitPatientMedicationResponseDTO> createVisitPatientMedication(@Validated @RequestBody VisitPatientMedicationRequestDTO requestDTO) {
        logger.info("Creating new visit patient medication for visitId: {}", requestDTO.getVisitId());
        VisitPatientMedicationResponseDTO created = visitPatientMedicationService.createVisitPatientMedication(requestDTO);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing visit patient medication.
     */
    @Operation(summary = "Update visit patient medication", description = "Updates an existing visit patient medication")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.MEDICATION_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitPatientMedicationResponseDTO> updateVisitPatientMedication(@PathVariable Long id, @Validated @RequestBody VisitPatientMedicationRequestDTO requestDTO) {
        logger.info("Updating visit patient medication id: {}", id);
        VisitPatientMedicationResponseDTO updated = visitPatientMedicationService.updateVisitPatientMedication(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a visit patient medication by ID.
     */
    @Operation(summary = "Delete visit patient medication", description = "Deletes a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.MEDICATION_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitPatientMedication(@PathVariable Long id) {
        logger.info("Deleting visit patient medication id: {}", id);
        visitPatientMedicationService.deleteVisitPatientMedication(id);
        return ResponseEntity.noContent().build();
    }
}
