package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.VisitPatientAllergyRequest;
import com.healthcare.mgnt.dto.VisitPatientAllergyResponse;
import com.healthcare.mgnt.service.Implementation.VisitPatientAllergyService;
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
 * Controller for visit patient allergy management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/visit-patient-allergies")
public class VisitPatientAllergyController {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientAllergyController.class);

    @Autowired
    private VisitPatientAllergyService visitPatientAllergyService;

    /**
     * Get all visit patient allergies (paginated).
     */
    @Operation(summary = "Get all visit patient allergies", description = "Returns a paginated list of visit patient allergies")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<VisitPatientAllergyResponse>> getAllVisitPatientAllergies(@RequestParam(defaultValue = "0") int page,
                                                                                         @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all visit patient allergies, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<VisitPatientAllergyResponse> allergies = visitPatientAllergyService.getAllVisitPatientAllergies(pageable);
            return ResponseEntity.ok(allergies);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient allergies: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get visit patient allergy by ID.
     */
    @Operation(summary = "Get visit patient allergy by ID", description = "Returns a visit patient allergy by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_ALLERGY_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VisitPatientAllergyResponse> getVisitPatientAllergyById(@PathVariable Long id) {
        logger.info("Fetching visit patient allergy by id: {}", id);
        try {
            VisitPatientAllergyResponse allergy = visitPatientAllergyService.getVisitPatientAllergyById(id);
            if (allergy == null) {
                logger.warn("Visit patient allergy not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(allergy);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient allergy by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new visit patient allergy.
     */
    @Operation(summary = "Create a new visit patient allergy", description = "Creates a new visit patient allergy")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<VisitPatientAllergyResponse> createVisitPatientAllergy(@Validated @RequestBody VisitPatientAllergyRequest requestDTO) {
        logger.info("Creating new visit patient allergy for visitId: {}", requestDTO.getVisitId());
        try {
            VisitPatientAllergyResponse created = visitPatientAllergyService.createVisitPatientAllergy(requestDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception ex) {
            logger.error("Error creating visit patient allergy: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing visit patient allergy.
     */
    @Operation(summary = "Update visit patient allergy", description = "Updates an existing visit patient allergy")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_ALLERGY_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VisitPatientAllergyResponse> updateVisitPatientAllergy(@PathVariable Long id, @Validated @RequestBody VisitPatientAllergyRequest requestDTO) {
        logger.info("Updating visit patient allergy id: {}", id);
        try {
            VisitPatientAllergyResponse updated = visitPatientAllergyService.updateVisitPatientAllergy(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            logger.error("Error updating visit patient allergy id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a visit patient allergy by ID.
     */
    @Operation(summary = "Delete visit patient allergy", description = "Deletes a visit patient allergy by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.VISIT_PATIENT_ALLERGY_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitPatientAllergy(@PathVariable Long id) {
        logger.info("Deleting visit patient allergy id: {}", id);
        try {
            visitPatientAllergyService.deleteVisitPatientAllergy(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting visit patient allergy id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
