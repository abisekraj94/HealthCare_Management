package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientMedicalHistoryRequest;
import com.healthcare.mgnt.dto.response.PatientMedicalHistoryResponse;
import com.healthcare.mgnt.entity.patient.PatientMedicalHistory;
import com.healthcare.mgnt.repository.patient.PatientMedicalHistoryRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for patient medical history management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class PatientMedicalHistoryService implements IPatientMedicalHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalHistoryService.class);
    @Autowired
    private PatientMedicalHistoryRepository patientMedicalHistoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts PatientMedicalHistoryRequestDTO to PatientMedicalHistory entity using ModelMapper.
     * @param dto PatientMedicalHistoryRequestDTO
     * @return PatientMedicalHistory entity
     */
    private PatientMedicalHistory toEntity(PatientMedicalHistoryRequest dto) {
        return modelMapper.map(dto, PatientMedicalHistory.class);
    }

    /**
     * Converts PatientMedicalHistory entity to PatientMedicalHistoryResponseDTO using ModelMapper.
     * @param entity PatientMedicalHistory entity
     * @return PatientMedicalHistoryResponseDTO
     */
    private PatientMedicalHistoryResponse toResponseDTO(PatientMedicalHistory entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, PatientMedicalHistoryResponse.class);
    }

    /**
     * Returns a paginated list of patient medical histories.
     * @param pageable Pageable object
     * @return Page of PatientMedicalHistoryResponseDTO
     */
    public Page<PatientMedicalHistoryResponse> getAllPatientMedicalHistories(Pageable pageable) {
        logger.info("Fetching all patient medical histories with pageable: {}", pageable);
        try {
            return patientMedicalHistoryRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching patient medical histories: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Returns a patient medical history by ID.
     * @param id History ID
     * @return PatientMedicalHistoryResponseDTO or null if not found
     */
    public PatientMedicalHistoryResponse getPatientMedicalHistoryById(Long id) {
        logger.info("Fetching patient medical history by id: {}", id);
        try {
            return patientMedicalHistoryRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching patient medical history by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Creates a new patient medical history record.
     * @param dto PatientMedicalHistoryRequestDTO
     * @return PatientMedicalHistoryResponseDTO of the created record
     */
    @Transactional
    public PatientMedicalHistoryResponse createPatientMedicalHistory(PatientMedicalHistoryRequest dto) {
        logger.info("Creating patient medical history: {}", dto);
        try {
            PatientMedicalHistory history = toEntity(dto);
            PatientMedicalHistory saved = patientMedicalHistoryRepository.save(history);
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating patient medical history: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Updates an existing patient medical history record.
     * @param id History ID
     * @param dto PatientMedicalHistoryRequestDTO
     * @return PatientMedicalHistoryResponseDTO of the updated record
     */
    @Transactional
    public PatientMedicalHistoryResponse updatePatientMedicalHistory(Long id, PatientMedicalHistoryRequest dto) {
        logger.info("Updating patient medical history with id {}: {}", id, dto);
        try {
            PatientMedicalHistory history = patientMedicalHistoryRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.MEDICAL_HISTORY_NOT_FOUND));
            modelMapper.map(dto, history);
            PatientMedicalHistory updated = patientMedicalHistoryRepository.save(history);
            return toResponseDTO(updated);
        } catch (Exception ex) {
            logger.error("Error updating patient medical history with id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Deletes a patient medical history record by ID.
     * @param id History ID
     */
    @Transactional
    public void deletePatientMedicalHistory(Long id) {
        logger.info("Deleting patient medical history with id: {}", id);
        try {
            patientMedicalHistoryRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Error deleting patient medical history with id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }
}
