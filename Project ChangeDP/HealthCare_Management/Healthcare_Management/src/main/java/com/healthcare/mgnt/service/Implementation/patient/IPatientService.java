package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientRequest;
import com.healthcare.mgnt.dto.response.PatientResponse;

import java.util.List;

/**
 * Service interface for managing patient records, including registration, updates, and deletion.
 * Provides CRUD operations and paginated retrieval of patient data.
 */
public interface IPatientService {
    /**
     * Retrieves a paginated list of all patients.
     *
     * @param page pagination information
     * @return paginated list of patient response DTOs
     */
    List<PatientResponse> getAllPatients(int page, int size);

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
     * @param dto patient request DTO with updated details
     * @return updated patient response DTO
     */
    PatientResponse updatePatient(PatientRequest dto);

    /**
     * Deletes a patient by their unique ID.
     * @param id patient ID
     */
    void deletePatient(Long id);
}
