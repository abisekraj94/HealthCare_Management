package com.healthcare.mgnt.service.Implementation.patient;

import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.dto.request.PatientRequest;
import com.healthcare.mgnt.dto.response.PatientResponse;
import com.healthcare.mgnt.entity.patient.Patient;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.repository.patient.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for patient management business logic.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Slf4j
@Service
public class PatientService implements IPatientService {

    //Constructor injection is preferred for mandatory dependencies.
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;;
    public PatientService(PatientRepository patientRepository,ModelMapper modelMapper)
    {
        this.patientRepository=patientRepository;
        this.modelMapper=modelMapper;
    }

    /**
     * Converts PatientRequestDTO to Patient entity using ModelMapper.
     *
     * @param dto PatientRequestDTO
     * @return Patient entity
     */
    private Patient toEntity(PatientRequest dto) {
        return modelMapper.map(dto, Patient.class);
    }

    /**
     * Converts Patient entity to PatientResponseDTO.
     *
     * @param entity Patient entity
     * @return PatientResponseDTO
     */
    private PatientResponse toResponseDTO(Patient entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, PatientResponse.class);
    }

    /**
     * Returns a paginated list of patients.
     *
     * @param page Pageable object
     * @param size Pageable object
     * @return Page of PatientResponseDTO
     */
    public List<PatientResponse> getAllPatients(int page, int size) {
        log.info("Fetching all patients with List");
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Patient> patientsPage = patientRepository.findAll(pageable);
            return patientsPage.stream().toList().
                    stream().map(this::toResponseDTO).toList();
        } catch (Exception ex) {
            log.error("Error fetching patients: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Returns a patient by ID.
     * @param id Patient ID
     * @return PatientResponseDTO or null if not found
     */
    public PatientResponse getPatientById(Long id) {
        log.info("Fetching patient by id: {}", id);
        try {
            return patientRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            log.error("Error fetching patient by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Creates a new patient.
     * @param dto PatientRequestDTO
     * @return Created PatientResponseDTO
     */
    @Transactional //it is recommended to use @Transactional to ensure data consistency and proper error handling.
    public PatientResponse createPatient(PatientRequest dto) {
        log.info("Creating patient with MRN: {}", dto.getMrn());
        try {
            Patient patient = toEntity(dto);
            Patient saved = patientRepository.save(patient);
            log.info("Patient created with id: {}", saved.getPatientId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            log.error("Error creating patient: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Updates an existing patient using ModelMapper for DTO to entity mapping.
     * @param dto PatientRequestDTO
     * @return Updated PatientResponseDTO
     */
    @Transactional
    public PatientResponse updatePatient(PatientRequest dto) {
        log.info("Updating patient with id: {}", dto.toString());
        try {
            Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new AppException(AppErrorCodes.PATIENT_NOT_FOUND));
            modelMapper.map(dto, patient);
            patientRepository.save(patient);
            log.info("Patient updated with id: {}", patient.getPatientId());
            // TODO: Send response as success
            return toResponseDTO(patient);
        } catch (AppException ex) { // handles your custom application exceptions
            log.error("Patient not found for update, id: {}", dto.getPatientId());
            throw ex;
        } catch (Exception ex) { //catches all other unexpected exceptions
            log.error("Error updating patient id {}: {}", dto.getPatientId(), ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Deletes a patient by ID.
     * @param id Patient ID
     */
    @Transactional
    public void deletePatient(Long id) {
        log.info("Deleting patient with id: {}", id);
        try {
            patientRepository.deleteByPatientId(id);
            log.info("Patient deleted with id: {}", id);
        } catch (AppException ex) {
            log.error("Patient not found for delete, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            log.error("Error deleting patient id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }
}
