package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientMedicalHistoryRequest;
import com.healthcare.mgnt.dto.response.PatientMedicalHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient medical histories, including diagnoses, treatments, and procedures.
 * Provides CRUD operations and paginated retrieval of medical history records.
 */
public interface IPatientMedicalHistoryService {
    /**
     * Retrieves a paginated list of all patient medical histories.
     * @param pageable pagination information
     * @return paginated list of patient medical history response DTOs
     */
    Page<PatientMedicalHistoryResponse> getAllPatientMedicalHistories(Pageable pageable);

    /**
     * Retrieves a patient medical history by its unique ID.
     * @param id medical history ID
     * @return patient medical history response DTO
     */
    PatientMedicalHistoryResponse getPatientMedicalHistoryById(Long id);

    /**
     * Creates a new patient medical history record.
     * @param dto medical history request DTO
     * @return created medical history response DTO
     */
    PatientMedicalHistoryResponse createPatientMedicalHistory(PatientMedicalHistoryRequest dto);

    /**
     * Updates an existing patient medical history record.
     * @param id medical history ID
     * @param dto medical history request DTO with updated details
     * @return updated medical history response DTO
     */
    PatientMedicalHistoryResponse updatePatientMedicalHistory(Long id, PatientMedicalHistoryRequest dto);

    /**
     * Deletes a patient medical history by its unique ID.
     * @param id medical history ID
     */
    void deletePatientMedicalHistory(Long id);
}
