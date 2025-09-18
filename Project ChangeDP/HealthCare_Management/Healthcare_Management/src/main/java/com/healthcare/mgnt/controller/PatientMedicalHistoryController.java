package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.PatientMedicalHistoryRequest;
import com.healthcare.mgnt.dto.PatientMedicalHistoryResponse;
import com.healthcare.mgnt.service.Implementation.PatientMedicalHistoryService;
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
 * Controller for patient medical history management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patient-medical-history")
public class PatientMedicalHistoryController {
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalHistoryController.class);

    @Autowired
    private PatientMedicalHistoryService patientMedicalHistoryService;

    /**
     * Get all patient medical histories (paginated).
     */
    @Operation(summary = "Get all patient medical histories", description = "Returns a paginated list of patient medical histories")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<PatientMedicalHistoryResponse>> getAllPatientMedicalHistories(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient medical histories, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PatientMedicalHistoryResponse> histories = patientMedicalHistoryService.getAllPatientMedicalHistories(pageable);
            return ResponseEntity.ok(histories);
        } catch (Exception ex) {
            logger.error("Error fetching patient medical histories: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get patient medical history by ID.
     */
    @Operation(summary = "Get patient medical history by ID", description = "Returns a patient medical history by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_MEDICAL_HISTORY_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientMedicalHistoryResponse> getPatientMedicalHistoryById(@PathVariable Long id) {
        logger.info("Fetching patient medical history by id: {}", id);
        try {
            PatientMedicalHistoryResponse history = patientMedicalHistoryService.getPatientMedicalHistoryById(id);
            if (history == null) {
                logger.warn("Patient medical history not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(history);
        } catch (Exception ex) {
            logger.error("Error fetching patient medical history by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new patient medical history.
     */
    @Operation(summary = "Create a new patient medical history", description = "Creates a new patient medical history")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<PatientMedicalHistoryResponse> createPatientMedicalHistory(@Validated @RequestBody PatientMedicalHistoryRequest requestDTO) {
        logger.info("Creating new patient medical history for patientId: {}", requestDTO.getPatientId());
        try {
            PatientMedicalHistoryResponse created = patientMedicalHistoryService.createPatientMedicalHistory(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating patient medical history: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing patient medical history.
     */
    @Operation(summary = "Update patient medical history", description = "Updates an existing patient medical history")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_MEDICAL_HISTORY_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientMedicalHistoryResponse> updatePatientMedicalHistory(@PathVariable Long id, @Validated @RequestBody PatientMedicalHistoryRequest requestDTO) {
        logger.info("Updating patient medical history id: {}", id);
        try {
            PatientMedicalHistoryResponse updated = patientMedicalHistoryService.updatePatientMedicalHistory(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient medical history id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a patient medical history by ID.
     */
    @Operation(summary = "Delete patient medical history", description = "Deletes a patient medical history by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_MEDICAL_HISTORY_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientMedicalHistory(@PathVariable Long id) {
        logger.info("Deleting patient medical history id: {}", id);
        try {
            patientMedicalHistoryService.deletePatientMedicalHistory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting patient medical history id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
