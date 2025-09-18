package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequest;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponse;
import com.healthcare.mgnt.entity.VisitPatientMedication;
import com.healthcare.mgnt.repository.VisitPatientMedicationRepository;
import com.healthcare.mgnt.repository.PatientVisitRepository;
import com.healthcare.mgnt.repository.UserRepository;
import com.healthcare.mgnt.exception.MedicationNotFoundException;
import com.healthcare.mgnt.constants.ApplicationConstants;
import com.healthcare.mgnt.service.IVisitPatientMedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

/**
 * Service class for visit patient medication management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class VisitPatientMedicationService implements IVisitPatientMedicationService {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientMedicationService.class);
    @Autowired
    private VisitPatientMedicationRepository visitPatientMedicationRepository;
    @Autowired
    private PatientVisitRepository patientVisitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts VisitPatientMedicationRequestDTO to VisitPatientMedication entity using ModelMapper.
     * @param dto VisitPatientMedicationRequestDTO
     * @return VisitPatientMedication entity
     */
    private VisitPatientMedication toEntity(VisitPatientMedicationRequest dto) {
        return modelMapper.map(dto, VisitPatientMedication.class);
    }

    /**
     * Converts VisitPatientMedication entity to VisitPatientMedicationResponseDTO using ModelMapper.
     * @param entity VisitPatientMedication entity
     * @return VisitPatientMedicationResponseDTO
     */
    private VisitPatientMedicationResponse toResponseDTO(VisitPatientMedication entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, VisitPatientMedicationResponse.class);
    }

    /**
     * Get all visit patient medications (paginated, DTO).
     */
    public Page<VisitPatientMedicationResponse> getAllVisitPatientMedications(Pageable pageable) {
        logger.info("Fetching all visit patient medications (paginated)");
        try {
            return visitPatientMedicationRepository.findAll(pageable)
                    .map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching visit patient medications: {}", ex.getMessage(), ex);
            throw new MedicationNotFoundException(ApplicationConstants.INTERNAL_ERROR);
        }
    }

    /**
     * Get visit patient medication by ID (DTO).
     */
    public VisitPatientMedicationResponse getVisitPatientMedicationById(Long id) {
        logger.info("Fetching visit patient medication by id: {}", id);
        try {
            VisitPatientMedication medication = visitPatientMedicationRepository.findById(id)
                    .orElseThrow(() -> new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND));
            return toResponseDTO(medication);
        } catch (MedicationNotFoundException ex) {
            logger.warn("Medication not found for id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error fetching visit patient medication by id {}: {}", id, ex.getMessage(), ex);
            throw new MedicationNotFoundException(ApplicationConstants.INTERNAL_ERROR);
        }
    }

    /**
     * Create a new visit patient medication.
     */
    @Transactional
    public VisitPatientMedicationResponse createVisitPatientMedication(VisitPatientMedicationRequest dto) {
        logger.info("Creating visit patient medication: {}", dto);
        try {
            VisitPatientMedication medication = toEntity(dto);
            VisitPatientMedication saved = visitPatientMedicationRepository.save(medication);
            logger.info("Visit patient medication created with id: {}", saved.getMedicationId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating visit patient medication: {}", ex.getMessage(), ex);
            throw new MedicationNotFoundException(ApplicationConstants.INTERNAL_ERROR);
        }
    }

    /**
     * Update an existing visit patient medication.
     */
    @Transactional
    public VisitPatientMedicationResponse updateVisitPatientMedication(Long id, VisitPatientMedicationRequest dto) {
        logger.info("Updating visit patient medication with id {}: {}", id, dto);
        try {
            VisitPatientMedication medication = visitPatientMedicationRepository.findById(id)
                    .orElseThrow(() -> new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND));
            modelMapper.map(dto, medication);
            VisitPatientMedication updated = visitPatientMedicationRepository.save(medication);
            logger.info("Visit patient medication updated with id: {}", updated.getMedicationId());
            return toResponseDTO(updated);
        } catch (MedicationNotFoundException ex) {
            logger.warn("Medication not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating visit patient medication id {}: {}", id, ex.getMessage(), ex);
            throw new MedicationNotFoundException(ApplicationConstants.INTERNAL_ERROR);
        }
    }

    /**
     * Delete a visit patient medication by ID.
     */
    @Transactional
    public void deleteVisitPatientMedication(Long id) {
        logger.info("Deleting visit patient medication with id: {}", id);
        try {
            if (!visitPatientMedicationRepository.existsById(id)) {
                logger.warn("Medication not found for delete, id: {}", id);
                throw new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND);
            }
            visitPatientMedicationRepository.deleteById(id);
            logger.info("Visit patient medication deleted with id: {}", id);
        } catch (MedicationNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting visit patient medication id {}: {}", id, ex.getMessage(), ex);
            throw new MedicationNotFoundException(ApplicationConstants.INTERNAL_ERROR);
        }
    }
}
