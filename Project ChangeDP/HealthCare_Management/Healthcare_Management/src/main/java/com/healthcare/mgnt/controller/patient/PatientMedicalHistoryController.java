package com.healthcare.mgnt.controller.patient;

import com.healthcare.mgnt.dto.request.PatientMedicalHistoryRequest;
import com.healthcare.mgnt.dto.response.PatientMedicalHistoryResponse;
import com.healthcare.mgnt.service.Implementation.patient.PatientMedicalHistoryService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

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
    public Page<PatientMedicalHistoryResponse> getAllPatientMedicalHistories(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient medical histories, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return patientMedicalHistoryService.getAllPatientMedicalHistories(pageable);
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
    public PatientMedicalHistoryResponse getPatientMedicalHistoryById(@PathVariable Long id) {
        logger.info("Fetching patient medical history by id: {}", id);
            PatientMedicalHistoryResponse history = patientMedicalHistoryService.getPatientMedicalHistoryById(id);
            if (history == null) {
                logger.error("Patient medical history not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient medical history not found");
            }
            return history;
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
    public PatientMedicalHistoryResponse createPatientMedicalHistory(@Validated @RequestBody PatientMedicalHistoryRequest requestDTO) {
        logger.info("Creating new patient medical history for patientId: {}", requestDTO.getPatientId());
        return patientMedicalHistoryService.createPatientMedicalHistory(requestDTO);
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
    public PatientMedicalHistoryResponse updatePatientMedicalHistory(@PathVariable Long id, @Validated @RequestBody PatientMedicalHistoryRequest requestDTO) {
        logger.info("Updating patient medical history id: {}", id);
        return patientMedicalHistoryService.updatePatientMedicalHistory(id, requestDTO);
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
    public void deletePatientMedicalHistory(@PathVariable Long id) {
        logger.info("Deleting patient medical history id: {}", id);
        patientMedicalHistoryService.deletePatientMedicalHistory(id);
    }
}
