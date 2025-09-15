package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.config.TenantContext;
import com.healthcare.mgnt.dto.PatientRequestDTO;
import com.healthcare.mgnt.dto.PatientResponseDTO;
import com.healthcare.mgnt.service.PatientService;
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
 * Controller for patient management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /**
     * Get all patients (paginated).
     */
    @Operation(summary = "Get all patients", description = "Returns a paginated list of patients")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        String tenant = TenantContext.getTenantId();
        logger.info("[Tenant: {}] Fetching all patients, page: {}, size: {}", tenant, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientResponseDTO> patients = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    /**
     * Get patient by ID.
     */
    @Operation(summary = "Get patient by ID", description = "Returns a patient by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient by id: {}", id);
        PatientResponseDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    /**
     * Create a new patient.
     */
    @Operation(summary = "Create a new patient", description = "Creates a new patient")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated @RequestBody PatientRequestDTO requestDTO) {
        logger.info("Creating new patient: {}", requestDTO.getMrn());
        PatientResponseDTO createdPatient = patientService.createPatient(requestDTO);
        return ResponseEntity.status(201).body(createdPatient);
    }

    /**
     * Update an existing patient.
     */
    @Operation(summary = "Update patient", description = "Updates an existing patient")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @Validated @RequestBody PatientRequestDTO requestDTO) {
        logger.info("Updating patient id: {}", id);
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, requestDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    /**
     * Delete a patient by ID.
     */
    @Operation(summary = "Delete patient", description = "Deletes a patient by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        logger.info("Deleting patient id: {}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
