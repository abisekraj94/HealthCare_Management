package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.VisitPatientDiagnosisRequest;
import com.healthcare.mgnt.dto.response.VisitPatientDiagnosisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient diagnosis records during visits.
 * Provides CRUD operations and paginated retrieval of diagnosis data for patient visits.
 */
public interface IVisitPatientDiagnosisService {
    /**
     * Retrieves a paginated list of all patient visit diagnosis records.
     * @param pageable pagination information
     * @return paginated list of visit patient diagnosis response DTOs
     */
    Page<VisitPatientDiagnosisResponse> getAllVisitPatientDiagnoses(Pageable pageable);

    /**
     * Retrieves a patient visit diagnosis record by its unique ID.
     * @param id diagnosis record ID
     * @return visit patient diagnosis response DTO
     */
    VisitPatientDiagnosisResponse getVisitPatientDiagnosisById(Long id);

    /**
     * Creates a new patient visit diagnosis record.
     * @param dto visit patient diagnosis request DTO
     * @return created visit patient diagnosis response DTO
     */
    VisitPatientDiagnosisResponse createVisitPatientDiagnosis(VisitPatientDiagnosisRequest dto);

    /**
     * Updates an existing patient visit diagnosis record.
     * @param id diagnosis record ID
     * @param dto visit patient diagnosis request DTO with updated details
     * @return updated visit patient diagnosis response DTO
     */
    VisitPatientDiagnosisResponse updateVisitPatientDiagnosis(Long id, VisitPatientDiagnosisRequest dto);

    /**
     * Deletes a patient visit diagnosis record by its unique ID.
     * @param id diagnosis record ID
     */
    void deleteVisitPatientDiagnosis(Long id);
}
