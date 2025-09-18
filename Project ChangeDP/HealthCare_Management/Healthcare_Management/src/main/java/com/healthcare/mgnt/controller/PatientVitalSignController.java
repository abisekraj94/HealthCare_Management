package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.PatientVitalSignRequest;
import com.healthcare.mgnt.dto.PatientVitalSignResponse;
import com.healthcare.mgnt.service.Implementation.PatientVitalSignService;
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
 * Controller for patient vital sign management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patient-vital-signs")
public class PatientVitalSignController {
    private static final Logger logger = LoggerFactory.getLogger(PatientVitalSignController.class);

    @Autowired
    private PatientVitalSignService patientVitalSignService;

    /**
     * Get all patient vital signs (paginated).
     */
    @Operation(summary = "Get all patient vital signs", description = "Returns a paginated list of patient vital signs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<PatientVitalSignResponse>> getAllPatientVitalSigns(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient vital signs, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PatientVitalSignResponse> vitalSigns = patientVitalSignService.getAllPatientVitalSigns(pageable);
            return ResponseEntity.ok(vitalSigns);
        } catch (Exception ex) {
            logger.error("Error fetching patient vital signs: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get patient vital sign by ID.
     */
    @Operation(summary = "Get patient vital sign by ID", description = "Returns a patient vital sign by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_VITAL_SIGN_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientVitalSignResponse> getPatientVitalSignById(@PathVariable Long id) {
        logger.info("Fetching patient vital sign by id: {}", id);
        try {
            PatientVitalSignResponse vitalSign = patientVitalSignService.getPatientVitalSignById(id);
            if (vitalSign == null) {
                logger.warn("Patient vital sign not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(vitalSign);
        } catch (Exception ex) {
            logger.error("Error fetching patient vital sign by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new patient vital sign.
     */
    @Operation(summary = "Create a new patient vital sign", description = "Creates a new patient vital sign")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<PatientVitalSignResponse> createPatientVitalSign(@Validated @RequestBody PatientVitalSignRequest requestDTO) {
        logger.info("Creating new patient vital sign for patientId: {}", requestDTO.getPatientId());
        try {
            PatientVitalSignResponse created = patientVitalSignService.createPatientVitalSign(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating patient vital sign: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing patient vital sign.
     */
    @Operation(summary = "Update patient vital sign", description = "Updates an existing patient vital sign")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_VITAL_SIGN_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientVitalSignResponse> updatePatientVitalSign(@PathVariable Long id, @Validated @RequestBody PatientVitalSignRequest requestDTO) {
        logger.info("Updating patient vital sign id: {}", id);
        try {
            PatientVitalSignResponse updated = patientVitalSignService.updatePatientVitalSign(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient vital sign id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a patient vital sign by ID.
     */
    @Operation(summary = "Delete patient vital sign", description = "Deletes a patient vital sign by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_VITAL_SIGN_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientVitalSign(@PathVariable Long id) {
        logger.info("Deleting patient vital sign id: {}", id);
        try {
            patientVitalSignService.deletePatientVitalSign(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting patient vital sign id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
