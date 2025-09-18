package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.VisitPatientAllergyRequest;
import com.healthcare.mgnt.dto.VisitPatientAllergyResponse;
import com.healthcare.mgnt.entity.VisitPatientAllergy;
import com.healthcare.mgnt.repository.VisitPatientAllergyRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IVisitPatientAllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for visit patient allergy management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class VisitPatientAllergyService implements IVisitPatientAllergyService {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientAllergyService.class);
    @Autowired
    private VisitPatientAllergyRepository visitPatientAllergyRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts VisitPatientAllergyRequestDTO to VisitPatientAllergy entity using ModelMapper.
     * @param dto VisitPatientAllergyRequestDTO
     * @return VisitPatientAllergy entity
     */
    private VisitPatientAllergy toEntity(VisitPatientAllergyRequest dto) {
        return modelMapper.map(dto, VisitPatientAllergy.class);
    }

    /**
     * Converts VisitPatientAllergy entity to VisitPatientAllergyResponseDTO using ModelMapper.
     * @param entity VisitPatientAllergy entity
     * @return VisitPatientAllergyResponseDTO
     */
    private VisitPatientAllergyResponse toResponseDTO(VisitPatientAllergy entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, VisitPatientAllergyResponse.class);
    }

    /**
     * Returns a paginated list of visit patient allergies.
     * @param pageable Pageable object
     * @return Page of VisitPatientAllergyResponseDTO
     */
    public Page<VisitPatientAllergyResponse> getAllVisitPatientAllergies(Pageable pageable) {
        logger.info("Fetching all visit patient allergies with pageable: {}", pageable);
        try {
            return visitPatientAllergyRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient allergies: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Returns a visit patient allergy by ID.
     * @param id Allergy ID
     * @return VisitPatientAllergyResponseDTO or null if not found
     */
    public VisitPatientAllergyResponse getVisitPatientAllergyById(Long id) {
        logger.info("Fetching visit patient allergy by id: {}", id);
        try {
            return visitPatientAllergyRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient allergy by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Creates a new visit patient allergy.
     * @param dto VisitPatientAllergyRequestDTO
     * @return Created VisitPatientAllergyResponseDTO
     */
    @Transactional
    public VisitPatientAllergyResponse createVisitPatientAllergy(VisitPatientAllergyRequest dto) {
        logger.info("Creating visit patient allergy: {}", dto);
        try {
            VisitPatientAllergy allergy = toEntity(dto);
            VisitPatientAllergy saved = visitPatientAllergyRepository.save(allergy);
            logger.info("Visit patient allergy created with id: {}", saved.getAllergyId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating visit patient allergy: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Updates an existing visit patient allergy.
     * @param id Allergy ID
     * @param dto VisitPatientAllergyRequestDTO
     * @return Updated VisitPatientAllergyResponseDTO
     */
    @Transactional
    public VisitPatientAllergyResponse updateVisitPatientAllergy(Long id, VisitPatientAllergyRequest dto) {
        logger.info("Updating visit patient allergy with id {}: {}", id, dto);
        try {
            VisitPatientAllergy allergy = visitPatientAllergyRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.ALLERGY_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, allergy);
            VisitPatientAllergy updated = visitPatientAllergyRepository.save(allergy);
            logger.info("Visit patient allergy updated with id: {}", updated.getAllergyId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Visit patient allergy not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating visit patient allergy id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Deletes a visit patient allergy by ID.
     * @param id Allergy ID
     */
    @Transactional
    public void deleteVisitPatientAllergy(Long id) {
        logger.info("Deleting visit patient allergy with id: {}", id);
        try {
            if (!visitPatientAllergyRepository.existsById(id)) {
                logger.warn("Visit patient allergy not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.ALLERGY_NOT_FOUND, "Failed to update patient medical history");
            }
            visitPatientAllergyRepository.deleteById(id);
            logger.info("Visit patient allergy deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting visit patient allergy id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }
}
