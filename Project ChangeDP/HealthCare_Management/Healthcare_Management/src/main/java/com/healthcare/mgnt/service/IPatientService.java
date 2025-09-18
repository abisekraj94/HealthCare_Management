package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientRequest;
import com.healthcare.mgnt.dto.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient records, including registration, updates, and deletion.
 * Provides CRUD operations and paginated retrieval of patient data.
 */
public interface IPatientService {
    /**
     * Retrieves a paginated list of all patients.
     * @param pageable pagination information
     * @return paginated list of patient response DTOs
     */
    Page<PatientResponse> getAllPatients(Pageable pageable);

    /**
     * Retrieves a patient by their unique ID.
     * @param id patient ID
     * @return patient response DTO
     */
    PatientResponse getPatientById(Long id);

    /**
     * Creates a new patient record.
     * @param dto patient request DTO
     * @return created patient response DTO
     */
    PatientResponse createPatient(PatientRequest dto);

    /**
     * Updates an existing patient record.
     * @param id patient ID
     * @param dto patient request DTO with updated details
     * @return updated patient response DTO
     */
    PatientResponse updatePatient(Long id, PatientRequest dto);

    /**
     * Deletes a patient by their unique ID.
     * @param id patient ID
     */
    void deletePatient(Long id);
}
