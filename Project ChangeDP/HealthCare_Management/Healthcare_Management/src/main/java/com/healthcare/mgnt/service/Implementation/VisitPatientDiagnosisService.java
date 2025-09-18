package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.VisitPatientDiagnosisRequest;
import com.healthcare.mgnt.dto.VisitPatientDiagnosisResponse;
import com.healthcare.mgnt.entity.VisitPatientDiagnosis;
import com.healthcare.mgnt.repository.VisitPatientDiagnosisRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IVisitPatientDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for visit patient diagnosis management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class VisitPatientDiagnosisService implements IVisitPatientDiagnosisService {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientDiagnosisService.class);
    @Autowired
    private VisitPatientDiagnosisRepository visitPatientDiagnosisRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts VisitPatientDiagnosisRequestDTO to VisitPatientDiagnosis entity using ModelMapper.
     * @param dto VisitPatientDiagnosisRequestDTO
     * @return VisitPatientDiagnosis entity
     */
    private VisitPatientDiagnosis toEntity(VisitPatientDiagnosisRequest dto) {
        return modelMapper.map(dto, VisitPatientDiagnosis.class);
    }

    /**
     * Converts VisitPatientDiagnosis entity to VisitPatientDiagnosisResponseDTO using ModelMapper.
     * @param entity VisitPatientDiagnosis entity
     * @return VisitPatientDiagnosisResponseDTO
     */
    private VisitPatientDiagnosisResponse toResponseDTO(VisitPatientDiagnosis entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, VisitPatientDiagnosisResponse.class);
    }

    /**
     * Returns a paginated list of visit patient diagnoses.
     * @param pageable Pageable object
     * @return Page of VisitPatientDiagnosisResponseDTO
     */
    public Page<VisitPatientDiagnosisResponse> getAllVisitPatientDiagnoses(Pageable pageable) {
        logger.info("Fetching all visit patient diagnoses with pageable: {}", pageable);
        try {
            return visitPatientDiagnosisRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient diagnoses: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Returns a visit patient diagnosis by ID.
     * @param id Diagnosis ID
     * @return VisitPatientDiagnosisResponseDTO or null if not found
     */
    public VisitPatientDiagnosisResponse getVisitPatientDiagnosisById(Long id) {
        logger.info("Fetching visit patient diagnosis by id: {}", id);
        try {
            return visitPatientDiagnosisRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient diagnosis by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Creates a new visit patient diagnosis.
     * @param dto VisitPatientDiagnosisRequestDTO
     * @return Created VisitPatientDiagnosisResponseDTO
     */
    @Transactional
    public VisitPatientDiagnosisResponse createVisitPatientDiagnosis(VisitPatientDiagnosisRequest dto) {
        logger.info("Creating visit patient diagnosis: {}", dto);
        try {
            VisitPatientDiagnosis diagnosis = toEntity(dto);
            VisitPatientDiagnosis saved = visitPatientDiagnosisRepository.save(diagnosis);
            logger.info("Visit patient diagnosis created with id: {}", saved.getDiagnosisId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating visit patient diagnosis: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Updates an existing visit patient diagnosis.
     * @param id Diagnosis ID
     * @param dto VisitPatientDiagnosisRequestDTO
     * @return Updated VisitPatientDiagnosisResponseDTO
     */
    @Transactional
    public VisitPatientDiagnosisResponse updateVisitPatientDiagnosis(Long id, VisitPatientDiagnosisRequest dto) {
        logger.info("Updating visit patient diagnosis with id {}: {}", id, dto);
        try {
            VisitPatientDiagnosis diagnosis = visitPatientDiagnosisRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.DIAGNOSIS_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, diagnosis);
            VisitPatientDiagnosis updated = visitPatientDiagnosisRepository.save(diagnosis);
            logger.info("Visit patient diagnosis updated with id: {}", updated.getDiagnosisId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Visit patient diagnosis not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating visit patient diagnosis id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Deletes a visit patient diagnosis by ID.
     * @param id Diagnosis ID
     */
    @Transactional
    public void deleteVisitPatientDiagnosis(Long id) {
        logger.info("Deleting visit patient diagnosis with id: {}", id);
        try {
            if (!visitPatientDiagnosisRepository.existsById(id)) {
                logger.warn("Visit patient diagnosis not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.DIAGNOSIS_NOT_FOUND, "Failed to update patient medical history");
            }
            visitPatientDiagnosisRepository.deleteById(id);
            logger.info("Visit patient diagnosis deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting visit patient diagnosis id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }
}
