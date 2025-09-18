package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.PatientIdentifierRequest;
import com.healthcare.mgnt.dto.PatientIdentifierResponse;
import com.healthcare.mgnt.entity.PatientIdentifier;
import com.healthcare.mgnt.repository.PatientIdentifierRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IPatientIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for patient identifier management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class PatientIdentifierService implements IPatientIdentifierService {
    private static final Logger logger = LoggerFactory.getLogger(PatientIdentifierService.class);
    @Autowired
    private PatientIdentifierRepository patientIdentifierRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts PatientIdentifierRequestDTO to PatientIdentifier entity using ModelMapper.
     * @param dto PatientIdentifierRequestDTO
     * @return PatientIdentifier entity
     */
    private PatientIdentifier toEntity(PatientIdentifierRequest dto) {
        return modelMapper.map(dto, PatientIdentifier.class);
    }

    /**
     * Converts PatientIdentifier entity to PatientIdentifierResponseDTO using ModelMapper.
     * @param entity PatientIdentifier entity
     * @return PatientIdentifierResponseDTO
     */
    private PatientIdentifierResponse toResponseDTO(PatientIdentifier entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, PatientIdentifierResponse.class);
    }

    /**
     * Returns a paginated list of patient identifiers.
     * @param pageable Pageable object
     * @return Page of PatientIdentifierResponseDTO
     */
    public Page<PatientIdentifierResponse> getAllPatientIdentifiers(Pageable pageable) {
        logger.info("Fetching all patient identifiers with pageable: {}", pageable);
        try {
            return patientIdentifierRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching patient identifiers: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Returns a patient identifier by ID.
     * @param id Identifier ID
     * @return PatientIdentifierResponseDTO or null if not found
     */
    public PatientIdentifierResponse getPatientIdentifierById(Long id) {
        logger.info("Fetching patient identifier by id: {}", id);
        try {
            return patientIdentifierRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching patient identifier by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Creates a new patient identifier.
     * @param dto PatientIdentifierRequestDTO
     * @return Created PatientIdentifierResponseDTO
     */
    @Transactional
    public PatientIdentifierResponse createPatientIdentifier(PatientIdentifierRequest dto) {
        logger.info("Creating patient identifier: {}", dto);
        try {
            PatientIdentifier identifier = toEntity(dto);
            PatientIdentifier saved = patientIdentifierRepository.save(identifier);
            logger.info("Patient identifier created with id: {}", saved.getIdentifierId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating patient identifier: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Updates an existing patient identifier.
     * @param id Identifier ID
     * @param dto PatientIdentifierRequestDTO
     * @return Updated PatientIdentifierResponseDTO
     */
    @Transactional
    public PatientIdentifierResponse updatePatientIdentifier(Long id, PatientIdentifierRequest dto) {
        logger.info("Updating patient identifier with id {}: {}", id, dto);
        try {
            PatientIdentifier identifier = patientIdentifierRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.IDENTIFIER_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, identifier);
            PatientIdentifier updated = patientIdentifierRepository.save(identifier);
            logger.info("Patient identifier updated with id: {}", updated.getIdentifierId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Patient identifier not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating patient identifier id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Deletes a patient identifier by ID.
     * @param id Identifier ID
     */
    @Transactional
    public void deletePatientIdentifier(Long id) {
        logger.info("Deleting patient identifier with id: {}", id);
        try {
            if (!patientIdentifierRepository.existsById(id)) {
                logger.warn("Patient identifier not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.IDENTIFIER_NOT_FOUND, "Failed to update patient medical history");
            }
            patientIdentifierRepository.deleteById(id);
            logger.info("Patient identifier deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting patient identifier id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }
}
