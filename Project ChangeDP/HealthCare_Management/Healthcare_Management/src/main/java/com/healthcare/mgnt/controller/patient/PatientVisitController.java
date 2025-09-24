package com.healthcare.mgnt.controller.patient;

import com.healthcare.mgnt.dto.request.PatientVisitRequest;
import com.healthcare.mgnt.dto.response.PatientVisitResponse;
import com.healthcare.mgnt.service.Implementation.patient.PatientVisitService;
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
    public Page<PatientVisitResponse> getAllPatientVisits(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all patient visits, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return patientVisitService.getAllPatientVisits(pageable);
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
    public PatientVisitResponse getPatientVisitById(@PathVariable Long id) {
        logger.info("Fetching patient visit by id: {}", id);
            PatientVisitResponse visit = patientVisitService.getPatientVisitById(id);
            if (visit == null) {
                logger.error("Patient visit not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient visit not found");
            }
            return visit;
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
    public PatientVisitResponse createPatientVisit(@Validated @RequestBody PatientVisitRequest requestDTO) {
        logger.info("Creating new patient visit for patientId: {}", requestDTO.getPatientId());
        return patientVisitService.createPatientVisit(requestDTO);
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
    public PatientVisitResponse updatePatientVisit(@PathVariable Long id, @Validated @RequestBody PatientVisitRequest requestDTO) {
        logger.info("Updating patient visit id: {}", id);
        return patientVisitService.updatePatientVisit(id, requestDTO);
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
    public void deletePatientVisit(@PathVariable Long id) {
        logger.info("Deleting patient visit id: {}", id);
            patientVisitService.deletePatientVisit(id);
    }
}
