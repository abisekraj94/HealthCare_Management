package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientVitalSignRequest;
import com.healthcare.mgnt.dto.response.PatientVitalSignResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing patient vital signs (e.g., blood pressure, heart rate).
 * Provides CRUD operations and paginated retrieval of vital sign records.
 */
public interface IPatientVitalSignService {
    /**
     * Retrieves a paginated list of all patient vital signs.
     * @param pageable pagination information
     * @return paginated list of patient vital sign response DTOs
     */
    Page<PatientVitalSignResponse> getAllPatientVitalSigns(Pageable pageable);

    /**
     * Retrieves a patient vital sign by its unique ID.
     * @param id vital sign ID
     * @return patient vital sign response DTO
     */
    PatientVitalSignResponse getPatientVitalSignById(Long id);

    /**
     * Creates a new patient vital sign record.
     * @param dto vital sign request DTO
     * @return created vital sign response DTO
     */
    PatientVitalSignResponse createPatientVitalSign(PatientVitalSignRequest dto);

    /**
     * Updates an existing patient vital sign record.
     * @param id vital sign ID
     * @param dto vital sign request DTO with updated details
     * @return updated vital sign response DTO
     */
    PatientVitalSignResponse updatePatientVitalSign(Long id, PatientVitalSignRequest dto);

    /**
     * Deletes a patient vital sign by its unique ID.
     * @param id vital sign ID
     */
    void deletePatientVitalSign(Long id);
}
