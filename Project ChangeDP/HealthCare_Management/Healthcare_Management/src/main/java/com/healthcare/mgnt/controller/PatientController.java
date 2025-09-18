package com.healthcare.mgnt.controller;

import com.multitenantlib.context.TenantContext;
import com.healthcare.mgnt.dto.PatientRequest;
import com.healthcare.mgnt.dto.PatientResponse;
import com.healthcare.mgnt.service.Implementation.PatientService;
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
    public ResponseEntity<Page<PatientResponse>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Fetching all patients, page: {}, size: {}", tenant, page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PatientResponse> patients = patientService.getAllPatients(pageable);
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            logger.error("Error fetching patients: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get patient by ID.
     */
    @Operation(summary = "Get patient by ID", description = "Returns a patient by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Fetching patient by id: {}", tenant, id);
        try {
            PatientResponse patient = patientService.getPatientById(id);
            if (patient == null) {
                logger.warn("Patient not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(patient);
        } catch (Exception ex) {
            logger.error("Error fetching patient by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<PatientResponse> createPatient(@Validated @RequestBody PatientRequest requestDTO) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Creating new patient", tenant);
        try {
            PatientResponse created = patientService.createPatient(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating patient: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
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
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @Validated @RequestBody PatientRequest requestDTO) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Updating patient id: {}", tenant, id);
        try {
            PatientResponse updated = patientService.updatePatient(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a patient by ID.
     */
    @Operation(summary = "Delete patient", description = "Deletes a patient by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Deleting patient id: {}", tenant, id);
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting patient id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
