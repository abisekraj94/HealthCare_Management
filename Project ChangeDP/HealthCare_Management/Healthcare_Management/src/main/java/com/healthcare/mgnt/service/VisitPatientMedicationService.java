package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponseDTO;
import com.healthcare.mgnt.entity.PatientVisit;
import com.healthcare.mgnt.entity.User;
import com.healthcare.mgnt.entity.VisitPatientMedication;
import com.healthcare.mgnt.repository.VisitPatientMedicationRepository;
import com.healthcare.mgnt.repository.PatientVisitRepository;
import com.healthcare.mgnt.repository.UserRepository;
import com.healthcare.mgnt.exception.MedicationNotFoundException;
import com.healthcare.mgnt.constants.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitPatientMedicationService {
    private static final Logger logger = LoggerFactory.getLogger(VisitPatientMedicationService.class);

    @Autowired
    private VisitPatientMedicationRepository visitPatientMedicationRepository;
    @Autowired
    private PatientVisitRepository patientVisitRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Get all visit patient medications (paginated, DTO).
     */
    public Page<VisitPatientMedicationResponseDTO> getAllVisitPatientMedications(Pageable pageable) {
        logger.info("Fetching all visit patient medications (paginated)");
        return visitPatientMedicationRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    /**
     * Get visit patient medication by ID (DTO).
     */
    public VisitPatientMedicationResponseDTO getVisitPatientMedicationById(Long id) {
        logger.info("Fetching visit patient medication by id: {}", id);
        VisitPatientMedication medication = visitPatientMedicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND));
        return toResponseDTO(medication);
    }

    /**
     * Create a new visit patient medication (DTO).
     */
    @Transactional
    public VisitPatientMedicationResponseDTO createVisitPatientMedication(VisitPatientMedicationRequestDTO requestDTO) {
        logger.info("Creating new visit patient medication for visitId: {}", requestDTO.getVisitId());
        VisitPatientMedication medication = toEntity(requestDTO);
        VisitPatientMedication saved = visitPatientMedicationRepository.save(medication);
        return toResponseDTO(saved);
    }

    /**
     * Update an existing visit patient medication (DTO).
     */
    @Transactional
    public VisitPatientMedicationResponseDTO updateVisitPatientMedication(Long id, VisitPatientMedicationRequestDTO requestDTO) {
        logger.info("Updating visit patient medication id: {}", id);
        VisitPatientMedication medication = visitPatientMedicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND));
        // Update fields from DTO
        medication.setMedicationName(requestDTO.getMedicationName());
        medication.setDosage(requestDTO.getDosage());
        medication.setFrequency(requestDTO.getFrequency());
        medication.setRoute(requestDTO.getRoute());
        medication.setStartDate(requestDTO.getStartDate());
        medication.setEndDate(requestDTO.getEndDate());
        if (requestDTO.getVisitId() != null) {
            PatientVisit visit = patientVisitRepository.findById(requestDTO.getVisitId())
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.VISIT_NOT_FOUND));
            medication.setVisit(visit);
        }
        if (requestDTO.getPrescribedById() != null) {
            User user = userRepository.findById(requestDTO.getPrescribedById())
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.USER_NOT_FOUND));
            medication.setPrescribedBy(user);
        }
        VisitPatientMedication updated = visitPatientMedicationRepository.save(medication);
        return toResponseDTO(updated);
    }

    /**
     * Delete a visit patient medication by ID.
     */
    @Transactional
    public void deleteVisitPatientMedication(Long id) {
        logger.info("Deleting visit patient medication id: {}", id);
        if (!visitPatientMedicationRepository.existsById(id)) {
            throw new MedicationNotFoundException(ApplicationConstants.MEDICATION_NOT_FOUND);
        }
        visitPatientMedicationRepository.deleteById(id);
    }

    // Mapping: Entity to ResponseDTO
    private VisitPatientMedicationResponseDTO toResponseDTO(VisitPatientMedication medication) {
        VisitPatientMedicationResponseDTO dto = new VisitPatientMedicationResponseDTO();
        dto.setMedicationId(medication.getMedicationId());
        dto.setVisitId(medication.getVisit() != null ? medication.getVisit().getVisitId() : null);
        dto.setMedicationName(medication.getMedicationName());
        dto.setDosage(medication.getDosage());
        dto.setFrequency(medication.getFrequency());
        dto.setRoute(medication.getRoute());
        dto.setStartDate(medication.getStartDate());
        dto.setEndDate(medication.getEndDate());
        dto.setPrescribedById(medication.getPrescribedBy() != null ? medication.getPrescribedBy().getUserId() : null);
        return dto;
    }

    // Mapping: RequestDTO to Entity
    private VisitPatientMedication toEntity(VisitPatientMedicationRequestDTO dto) {
        VisitPatientMedication medication = new VisitPatientMedication();
        if (dto.getVisitId() != null) {
            PatientVisit visit = patientVisitRepository.findById(dto.getVisitId())
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.VISIT_NOT_FOUND));
            medication.setVisit(visit);
        }
        medication.setMedicationName(dto.getMedicationName());
        medication.setDosage(dto.getDosage());
        medication.setFrequency(dto.getFrequency());
        medication.setRoute(dto.getRoute());
        medication.setStartDate(dto.getStartDate());
        medication.setEndDate(dto.getEndDate());
        if (dto.getPrescribedById() != null) {
            User user = userRepository.findById(dto.getPrescribedById())
                .orElseThrow(() -> new RuntimeException(ApplicationConstants.USER_NOT_FOUND));
            medication.setPrescribedBy(user);
        }
        return medication;
    }
}
