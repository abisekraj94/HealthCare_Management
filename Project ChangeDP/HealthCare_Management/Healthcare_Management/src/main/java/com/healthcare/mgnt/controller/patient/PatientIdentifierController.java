package com.healthcare.mgnt.controller.patient;

import com.healthcare.mgnt.dto.common.BaseResponse;
import com.healthcare.mgnt.dto.request.PatientIdentifierRequest;
import com.healthcare.mgnt.dto.response.PatientIdentifierResponse;
import com.healthcare.mgnt.service.Implementation.patient.PatientIdentifierService;
import com.healthcare.mgnt.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Controller for patient identifier management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/patient-identifiers")
public class PatientIdentifierController {
    private static final Logger logger = LoggerFactory.getLogger(PatientIdentifierController.class);

    @Autowired
    private PatientIdentifierService patientIdentifierService;

    /**
     * Get all patient identifiers (paginated).
     */
    @Operation(summary = "Get all patient identifiers", description = "Returns a paginated list of patient identifiers")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public BaseResponse<List<PatientIdentifierResponse>> getAllPatientIdentifiers(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient identifiers, page: {}, size: {}", page, size);
        return new BaseResponse<>(true, "Success", patientIdentifierService.getAllPatientIdentifiers(page, size));
    }

    /**
     * Get patient identifier by ID.
     */
    @Operation(summary = "Get patient identifier by ID", description = "Returns a patient identifier by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public PatientIdentifierResponse getPatientIdentifierById(@PathVariable Long id) {
        logger.info("Fetching patient identifier by id: {}", id);
            PatientIdentifierResponse identifier = patientIdentifierService.getPatientIdentifierById(id);
            if (identifier == null) {
                logger.error("Patient identifier not found for id: {}", id);
            }
            return identifier;
    }

    /**
     * Create a new patient identifier.
     */
    @Operation(summary = "Create a new patient identifier", description = "Creates a new patient identifier")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public PatientIdentifierResponse createPatientIdentifier(@Validated @RequestBody PatientIdentifierRequest requestDTO) {
        logger.info("Creating new patient identifier for patientId: {}", requestDTO.getPatientId());
            return patientIdentifierService.createPatientIdentifier(requestDTO);
    }

    /**
     * Update an existing patient identifier.
     */
    @Operation(summary = "Update patient identifier", description = "Updates an existing patient identifier")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public PatientIdentifierResponse updatePatientIdentifier(@PathVariable Long id, @Validated @RequestBody PatientIdentifierRequest requestDTO) {
            logger.info("Updating patient identifier id: {}", id);
            return patientIdentifierService.updatePatientIdentifier(id, requestDTO);

    }

    /**
     * Delete a patient identifier by ID.
     */
    @Operation(summary = "Delete patient identifier", description = "Deletes a patient identifier by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.PATIENT_IDENTIFIER_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientIdentifier(@PathVariable Long id) {
        logger.info("Deleting patient identifier id: {}", id);
            patientIdentifierService.deletePatientIdentifier(id);
    }
}
