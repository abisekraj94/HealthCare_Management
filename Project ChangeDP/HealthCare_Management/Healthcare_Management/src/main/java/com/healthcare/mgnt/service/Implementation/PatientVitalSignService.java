package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.PatientVitalSignRequest;
import com.healthcare.mgnt.dto.PatientVitalSignResponse;
import com.healthcare.mgnt.entity.PatientVitalSign;
import com.healthcare.mgnt.repository.PatientVitalSignRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IPatientVitalSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for patient vital sign management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class PatientVitalSignService implements IPatientVitalSignService {
    private static final Logger logger = LoggerFactory.getLogger(PatientVitalSignService.class);
    @Autowired
    private PatientVitalSignRepository patientVitalSignRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts PatientVitalSignRequestDTO to PatientVitalSign entity using ModelMapper.
     * @param dto PatientVitalSignRequestDTO
     * @return PatientVitalSign entity
     */
    private PatientVitalSign toEntity(PatientVitalSignRequest dto) {
        return modelMapper.map(dto, PatientVitalSign.class);
    }

    /**
     * Converts PatientVitalSign entity to PatientVitalSignResponseDTO using ModelMapper.
     * @param entity PatientVitalSign entity
     * @return PatientVitalSignResponseDTO
     */
    private PatientVitalSignResponse toResponseDTO(PatientVitalSign entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, PatientVitalSignResponse.class);
    }

    /**
     * Returns a paginated list of patient vital signs.
     * @param pageable Pageable object
     * @return Page of PatientVitalSignResponseDTO
     */
    public Page<PatientVitalSignResponse> getAllPatientVitalSigns(Pageable pageable) {
        logger.info("Fetching all patient vital signs with pageable: {}", pageable);
        try {
            return patientVitalSignRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching patient vital signs: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch patient vital signs");
        }
    }

    /**
     * Returns a patient vital sign by ID.
     * @param id VitalSign ID
     * @return PatientVitalSignResponseDTO or null if not found
     */
    public PatientVitalSignResponse getPatientVitalSignById(Long id) {
        logger.info("Fetching patient vital sign by id: {}", id);
        try {
            return patientVitalSignRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching patient vital sign by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch patient vital sign");
        }
    }

    /**
     * Creates a new patient vital sign record.
     * @param dto PatientVitalSignRequestDTO
     * @return PatientVitalSignResponseDTO
     */
    @Transactional
    public PatientVitalSignResponse createPatientVitalSign(PatientVitalSignRequest dto) {
        logger.info("Creating patient vital sign: {}", dto);
        try {
            PatientVitalSign vitalSign = toEntity(dto);
            PatientVitalSign saved = patientVitalSignRepository.save(vitalSign);
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating patient vital sign: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to create patient vital sign");
        }
    }

    /**
     * Updates an existing patient vital sign record.
     * @param id VitalSign ID
     * @param dto PatientVitalSignRequestDTO
     * @return PatientVitalSignResponseDTO
     */
    @Transactional
    public PatientVitalSignResponse updatePatientVitalSign(Long id, PatientVitalSignRequest dto) {
        logger.info("Updating patient vital sign with id {}: {}", id, dto);
        try {
            PatientVitalSign vitalSign = patientVitalSignRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.VITAL_SIGN_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, vitalSign);
            PatientVitalSign updated = patientVitalSignRepository.save(vitalSign);
            return toResponseDTO(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient vital sign with id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient vital sign");
        }
    }

    /**
     * Deletes a patient vital sign record by ID.
     * @param id VitalSign ID
     */
    @Transactional
    public void deletePatientVitalSign(Long id) {
        logger.info("Deleting patient vital sign with id: {}", id);
        try {
            patientVitalSignRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting patient vital sign with id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to delete patient vital sign");
        }
    }
}
