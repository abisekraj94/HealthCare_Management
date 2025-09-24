package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientIdentifierRequest;
import com.healthcare.mgnt.dto.response.PatientIdentifierResponse;

import java.util.List;

/**
 * Service interface for managing patient identifiers (e.g., medical record numbers, insurance IDs).
 * Provides CRUD operations and paginated retrieval of patient identifiers.
 */
public interface IPatientIdentifierService {
    /**
     * Retrieves a paginated list of all patient identifiers.
     * @return paginated list of patient identifier response DTOs
     */
    List<PatientIdentifierResponse> getAllPatientIdentifiers(int page, int size);

    /**
     * Retrieves a patient identifier by its unique ID.
     * @param id patient identifier ID
     * @return patient identifier response DTO
     */
    PatientIdentifierResponse getPatientIdentifierById(Long id);

    /**
     * Creates a new patient identifier record.
     * @param dto patient identifier request DTO
     * @return created patient identifier response DTO
     */
    PatientIdentifierResponse createPatientIdentifier(PatientIdentifierRequest dto);

    /**
     * Updates an existing patient identifier record.
     * @param id patient identifier ID
     * @param dto patient identifier request DTO with updated details
     * @return updated patient identifier response DTO
     */
    PatientIdentifierResponse updatePatientIdentifier(Long id, PatientIdentifierRequest dto);

    /**
     * Deletes a patient identifier by its unique ID.
     * @param id patient identifier ID
     */
    void deletePatientIdentifier(Long id);
}
