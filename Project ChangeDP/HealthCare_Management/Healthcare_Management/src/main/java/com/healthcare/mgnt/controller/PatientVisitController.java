package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.PatientVisitRequestDTO;
import com.healthcare.mgnt.dto.PatientVisitResponseDTO;
import com.healthcare.mgnt.service.PatientVisitService;
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
    public ResponseEntity<Page<PatientVisitResponseDTO>> getAllPatientVisits(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient visits, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientVisitResponseDTO> visits = patientVisitService.getAllPatientVisits(pageable);
        return ResponseEntity.ok(visits);
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
    public ResponseEntity<PatientVisitResponseDTO> getPatientVisitById(@PathVariable Long id) {
        logger.info("Fetching patient visit by id: {}", id);
        PatientVisitResponseDTO visit = patientVisitService.getPatientVisitById(id);
        return ResponseEntity.ok(visit);
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
    public ResponseEntity<PatientVisitResponseDTO> createPatientVisit(@Validated @RequestBody PatientVisitRequestDTO requestDTO) {
        logger.info("Creating new patient visit for patientId: {}", requestDTO.getPatientId());
        PatientVisitResponseDTO created = patientVisitService.createPatientVisit(requestDTO);
        return ResponseEntity.status(201).body(created);
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
    public ResponseEntity<PatientVisitResponseDTO> updatePatientVisit(@PathVariable Long id, @Validated @RequestBody PatientVisitRequestDTO requestDTO) {
        logger.info("Updating patient visit id: {}", id);
        PatientVisitResponseDTO updated = patientVisitService.updatePatientVisit(id, requestDTO);
        return ResponseEntity.ok(updated);
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
        patientVisitService.deletePatientVisit(id);
        return ResponseEntity.noContent().build();
    }
}
