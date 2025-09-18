package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequest;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient medication records during visits.
 * Provides CRUD operations and paginated retrieval of medication data for patient visits.
 */
public interface IVisitPatientMedicationService {
    /**
     * Retrieves a paginated list of all patient visit medication records.
     * @param pageable pagination information
     * @return paginated list of visit patient medication response DTOs
     */
    Page<VisitPatientMedicationResponse> getAllVisitPatientMedications(Pageable pageable);

    /**
     * Retrieves a patient visit medication record by its unique ID.
     * @param id medication record ID
     * @return visit patient medication response DTO
     */
    VisitPatientMedicationResponse getVisitPatientMedicationById(Long id);

    /**
     * Creates a new patient visit medication record.
     * @param dto visit patient medication request DTO
     * @return created visit patient medication response DTO
     */
    VisitPatientMedicationResponse createVisitPatientMedication(VisitPatientMedicationRequest dto);

    /**
     * Updates an existing patient visit medication record.
     * @param id medication record ID
     * @param dto visit patient medication request DTO with updated details
     * @return updated visit patient medication response DTO
     */
    VisitPatientMedicationResponse updateVisitPatientMedication(Long id, VisitPatientMedicationRequest dto);

    /**
     * Deletes a patient visit medication record by its unique ID.
     * @param id medication record ID
     */
    void deleteVisitPatientMedication(Long id);
}
