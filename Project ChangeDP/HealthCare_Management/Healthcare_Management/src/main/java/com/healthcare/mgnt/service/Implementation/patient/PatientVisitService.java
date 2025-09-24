package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.dto.request.PatientVisitRequest;
import com.healthcare.mgnt.dto.response.PatientVisitResponse;
import com.healthcare.mgnt.entity.patient.PatientVisit;
import com.healthcare.mgnt.repository.patient.PatientVisitRepository;
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
 * Service class for patient visit management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class PatientVisitService implements IPatientVisitService {
    private static final Logger logger = LoggerFactory.getLogger(PatientVisitService.class);
    @Autowired
    private PatientVisitRepository patientVisitRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts PatientVisitRequestDTO to PatientVisit entity using ModelMapper.
     * @param dto PatientVisitRequestDTO
     * @return PatientVisit entity
     */
    private PatientVisit toEntity(PatientVisitRequest dto) {
        return modelMapper.map(dto, PatientVisit.class);
    }

    /**
     * Converts PatientVisit entity to PatientVisitResponseDTO using ModelMapper.
     * @param entity PatientVisit entity
     * @return PatientVisitResponseDTO
     */
    private PatientVisitResponse toResponseDTO(PatientVisit entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, PatientVisitResponse.class);
    }

    /**
     * Returns a paginated list of patient visits.
     * @param pageable Pageable object
     * @return Page of PatientVisitResponseDTO
     */
    public Page<PatientVisitResponse> getAllPatientVisits(Pageable pageable) {
        logger.info("Fetching all patient visits with pageable: {}", pageable);
        try {
            return patientVisitRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching patient visits: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Returns a patient visit by ID.
     * @param id Visit ID
     * @return PatientVisitResponseDTO or null if not found
     */
    public PatientVisitResponse getPatientVisitById(Long id) {
        logger.info("Fetching patient visit by id: {}", id);
        try {
            return patientVisitRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching patient visit by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Creates a new patient visit.
     * @param dto PatientVisitRequestDTO
     * @return Created PatientVisitResponseDTO
     */
    @Transactional
    public PatientVisitResponse createPatientVisit(PatientVisitRequest dto) {
        logger.info("Creating patient visit: {}", dto);
        try {
            PatientVisit visit = toEntity(dto);
            PatientVisit saved = patientVisitRepository.save(visit);
            logger.info("Patient visit created with id: {}", saved.getVisitId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating patient visit: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Updates an existing patient visit.
     * @param id Visit ID
     * @param dto PatientVisitRequestDTO
     * @return Updated PatientVisitResponseDTO
     */
    @Transactional
    public PatientVisitResponse updatePatientVisit(Long id, PatientVisitRequest dto) {
        logger.info("Updating patient visit with id {}: {}", id, dto);
        try {
            PatientVisit visit = patientVisitRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.VISIT_NOT_FOUND));
            modelMapper.map(dto, visit);
            PatientVisit updated = patientVisitRepository.save(visit);
            logger.info("Patient visit updated with id: {}", updated.getVisitId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Patient visit not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating patient visit id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Deletes a patient visit by ID.
     * @param id Visit ID
     */
    @Transactional
    public void deletePatientVisit(Long id) {
        logger.info("Deleting patient visit with id: {}", id);
        try {
            if (!patientVisitRepository.existsById(id)) {
                logger.warn("Patient visit not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.VISIT_NOT_FOUND);
            }
            patientVisitRepository.deleteById(id);
            logger.info("Patient visit deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting patient visit id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }
}
