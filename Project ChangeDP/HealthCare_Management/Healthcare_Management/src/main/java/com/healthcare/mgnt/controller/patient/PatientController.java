package com.healthcare.mgnt.controller.patient;

import com.healthcare.mgnt.constants.ApplicationConstants;
import com.healthcare.mgnt.dto.request.PatientRequest;
import com.healthcare.mgnt.dto.response.PatientResponse;
import com.healthcare.mgnt.dto.common.BaseResponse;
import com.healthcare.mgnt.service.Implementation.patient.PatientService;
import com.multitenantlib.context.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for patient management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Get all patients (paginated).
     */
    @Operation(summary = "Get all patients", description = "Returns a paginated list of patients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public BaseResponse<List<PatientResponse>> getAllPatients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Fetching all patients, page: {}, size: {}", tenant, page, size);
        return new BaseResponse<>(true, "Success", patientService.getAllPatients(page, size));
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
    public BaseResponse<PatientResponse> getPatientById(@PathVariable Long id) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Fetching patient by id: {}", tenant, id);
        return new BaseResponse<>(true, "Patient fetched successfully", patientService.getPatientById(id));
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
    public BaseResponse<PatientResponse> createPatient(@Validated @RequestBody PatientRequest requestDTO) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Creating new patient", tenant);
        return new BaseResponse<>(true, "Patient created successfully", patientService.createPatient(requestDTO));
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
    public BaseResponse<Void> updatePatient(@Validated @RequestBody PatientRequest requestDTO) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Updating patient id: {}", tenant, requestDTO.getPatientId());
        return new BaseResponse<>(true, "Patient updated successfully", null);
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
    public BaseResponse<Void> deletePatient(@PathVariable Long id) {
        String tenant = TenantContext.getCurrentTenant();
        logger.info("[Tenant: {}] Deleting patient id: {}", tenant, id);
        patientService.deletePatient(id);
        return new BaseResponse<>(true, "Patient deleted successfully", null);
    }
}
