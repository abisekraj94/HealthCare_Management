package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientVisitRequest;
import com.healthcare.mgnt.dto.response.PatientVisitResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient visit records, including scheduling, updating, and deleting visits.
 * Provides CRUD operations and paginated retrieval of visit data.
 */
public interface IPatientVisitService {
    /**
     * Retrieves a paginated list of all patient visits.
     * @param pageable pagination information
     * @return paginated list of patient visit response DTOs
     */
    Page<PatientVisitResponse> getAllPatientVisits(Pageable pageable);

    /**
     * Retrieves a patient visit by its unique ID.
     * @param id visit ID
     * @return patient visit response DTO
     */
    PatientVisitResponse getPatientVisitById(Long id);

    /**
     * Creates a new patient visit record.
     * @param dto visit request DTO
     * @return created patient visit response DTO
     */
    PatientVisitResponse createPatientVisit(PatientVisitRequest dto);

    /**
     * Updates an existing patient visit record.
     * @param id visit ID
     * @param dto visit request DTO with updated details
     * @return updated patient visit response DTO
     */
    PatientVisitResponse updatePatientVisit(Long id, PatientVisitRequest dto);

    /**
     * Deletes a patient visit by its unique ID.
     * @param id visit ID
     */
    void deletePatientVisit(Long id);
}
