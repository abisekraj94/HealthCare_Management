package com.healthcare.mgnt.controller.patient.medication;

import com.healthcare.mgnt.dto.request.VisitPatientMedicationRequest;
import com.healthcare.mgnt.dto.response.VisitPatientMedicationResponse;
import com.healthcare.mgnt.service.Implementation.patient.VisitPatientMedicationService;
import com.healthcare.mgnt.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for visit patient medication management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/visit-patient-medications")
public class VisitPatientMedicationController {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientMedicationController.class);

    private final VisitPatientMedicationService visitPatientMedicationService;

    public VisitPatientMedicationController(VisitPatientMedicationService visitPatientMedicationService) {
        this.visitPatientMedicationService =  visitPatientMedicationService;
    }

    /**
     * Get all visit patient medications (paginated).
     */
    @Operation(summary = "Get all visit patient medications", description = "Returns a paginated list of visit patient medications")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public Page<VisitPatientMedicationResponse> getAllVisitPatientMedications(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient medications, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return visitPatientMedicationService.getAllVisitPatientMedications(pageable);
    }

    /**
     * Get visit patient medication by ID.
     */
    @Operation(summary = "Get visit patient medication by ID", description = "Returns a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public VisitPatientMedicationResponse getVisitPatientMedicationById(@PathVariable Long id) {
        logger.info("Fetching visit patient medication by id: {}", id);
        VisitPatientMedicationResponse medication = visitPatientMedicationService.getVisitPatientMedicationById(id);
            if (medication == null) {
                logger.error("Visit patient medication not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit patient medication not found");
            }
            return medication;
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
    public VisitPatientMedicationResponse createVisitPatientMedication(@Validated @RequestBody VisitPatientMedicationRequest requestDTO) {
        logger.info("Creating new visit patient medication for visitId: {}", requestDTO.getVisitId());
        return visitPatientMedicationService.createVisitPatientMedication(requestDTO);
    }

    /**
     * Update an existing visit patient medication.
     */
    @Operation(summary = "Update visit patient medication", description = "Updates an existing visit patient medication")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public VisitPatientMedicationResponse updateVisitPatientMedication(@PathVariable Long id, @Validated @RequestBody VisitPatientMedicationRequest requestDTO) {
        logger.info("Updating visit patient medication id: {}", id);
        return visitPatientMedicationService.updateVisitPatientMedication(id, requestDTO);
    }

    /**
     * Delete a visit patient medication by ID.
     */
    @Operation(summary = "Delete visit patient medication", description = "Deletes a visit patient medication by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_MEDICATION_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public void deleteVisitPatientMedication(@PathVariable Long id) {
        logger.info("Deleting visit patient medication id: {}", id);
        visitPatientMedicationService.deleteVisitPatientMedication(id);
    }
}
