package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.PatientRequest;
import com.healthcare.mgnt.dto.PatientResponse;
import com.healthcare.mgnt.entity.Patient;
import com.healthcare.mgnt.repository.PatientRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;

/**
 * Service class for patient management business logic.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class PatientService implements IPatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts PatientRequestDTO to Patient entity using ModelMapper.
     * @param dto PatientRequestDTO
     * @return Patient entity
     */
    private Patient toEntity(PatientRequest dto) {
        return modelMapper.map(dto, Patient.class);
    }

    /**
     * Converts Patient entity to PatientResponseDTO.
     * @param entity Patient entity
     * @return PatientResponseDTO
     */
    private PatientResponse toResponseDTO(Patient entity) {
        if (entity == null) return null;
        PatientResponse dto = new PatientResponse();
        dto.setPatientId(entity.getPatientId());
        dto.setMrn(entity.getMrn());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDob(entity.getDob());
        dto.setGender(entity.getGender());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        // You may need to set primaryPhysicianId/referralPhysicianId if needed
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    /**
     * Returns a paginated list of patients.
     * @param pageable Pageable object
     * @return Page of PatientResponseDTO
     */
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        logger.info("Fetching all patients with pageable: {}", pageable);
        try {
            return patientRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching patients: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch patients");
        }
    }

    /**
     * Returns a patient by ID.
     * @param id Patient ID
     * @return PatientResponseDTO or null if not found
     */
    public PatientResponse getPatientById(Long id) {
        logger.info("Fetching patient by id: {}", id);
        try {
            return patientRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching patient by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch patient");
        }
    }

    /**
     * Creates a new patient.
     * @param dto PatientRequestDTO
     * @return Created PatientResponseDTO
     */
    @Transactional
    public PatientResponse createPatient(PatientRequest dto) {
        logger.info("Creating patient with MRN: {}", dto.getMrn());
        try {
            Patient patient = toEntity(dto);
            Patient saved = patientRepository.save(patient);
            logger.info("Patient created with id: {}", saved.getPatientId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating patient: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to create patient");
        }
    }

    /**
     * Updates an existing patient using ModelMapper for DTO to entity mapping.
     * @param id Patient ID
     * @param dto PatientRequestDTO
     * @return Updated PatientResponseDTO
     */
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest dto) {
        logger.info("Updating patient with id: {}", id);
        try {
            Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.PATIENT_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, patient);
            Patient updated = patientRepository.save(patient);
            logger.info("Patient updated with id: {}", updated.getPatientId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Patient not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating patient id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient");
        }
    }

    /**
     * Deletes a patient by ID.
     * @param id Patient ID
     */
    @Transactional
    public void deletePatient(Long id) {
        logger.info("Deleting patient with id: {}", id);
        try {
            if (!patientRepository.existsById(id)) {
                logger.warn("Patient not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.PATIENT_NOT_FOUND, "Failed to update patient medical history");
            }
            patientRepository.deleteById(id);
            logger.info("Patient deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting patient id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to delete patient");
        }
    }
}
