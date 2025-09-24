package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.VisitPatientAllergyRequest;
import com.healthcare.mgnt.dto.response.VisitPatientAllergyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient allergy records during visits.
 * Provides CRUD operations and paginated retrieval of allergy data for patient visits.
 */
public interface IVisitPatientAllergyService {
    /**
     * Retrieves a paginated list of all patient visit allergy records.
     * @param pageable pagination information
     * @return paginated list of visit patient allergy response DTOs
     */
    Page<VisitPatientAllergyResponse> getAllVisitPatientAllergies(Pageable pageable);

    /**
     * Retrieves a patient visit allergy record by its unique ID.
     * @param id allergy record ID
     * @return visit patient allergy response DTO
     */
    VisitPatientAllergyResponse getVisitPatientAllergyById(Long id);

    /**
     * Creates a new patient visit allergy record.
     * @param dto visit patient allergy request DTO
     * @return created visit patient allergy response DTO
     */
    VisitPatientAllergyResponse createVisitPatientAllergy(VisitPatientAllergyRequest dto);

    /**
     * Updates an existing patient visit allergy record.
     * @param id allergy record ID
     * @param dto visit patient allergy request DTO with updated details
     * @return updated visit patient allergy response DTO
     */
    VisitPatientAllergyResponse updateVisitPatientAllergy(Long id, VisitPatientAllergyRequest dto);

    /**
     * Deletes a patient visit allergy record by its unique ID.
     * @param id allergy record ID
     */
    void deleteVisitPatientAllergy(Long id);
}
