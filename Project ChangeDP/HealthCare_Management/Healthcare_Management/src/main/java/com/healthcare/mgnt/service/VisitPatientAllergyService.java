package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.VisitPatientAllergyRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientAllergyResponseDTO;
import com.healthcare.mgnt.entity.VisitPatientAllergy;
import com.healthcare.mgnt.repository.VisitPatientAllergyRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitPatientAllergyService {
    @Autowired
    private VisitPatientAllergyRepository visitPatientAllergyRepository;

    private VisitPatientAllergy toEntity(VisitPatientAllergyRequestDTO dto) {
        VisitPatientAllergy allergy = new VisitPatientAllergy();
        // You may need to set visit by ID lookup if needed
        allergy.setAllergyName(dto.getAllergyName());
        allergy.setReaction(dto.getReaction());
        allergy.setSeverity(dto.getSeverity());
        allergy.setNotes(dto.getNotes());
        return allergy;
    }

    private VisitPatientAllergyResponseDTO toResponseDTO(VisitPatientAllergy entity) {
        if (entity == null) return null;
        VisitPatientAllergyResponseDTO dto = new VisitPatientAllergyResponseDTO();
        dto.setAllergyId(entity.getAllergyId());
        // You may need to set visitId if needed
        dto.setAllergyName(entity.getAllergyName());
        dto.setReaction(entity.getReaction());
        dto.setSeverity(entity.getSeverity());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    public Page<VisitPatientAllergyResponseDTO> getAllVisitPatientAllergies(Pageable pageable) {
        return visitPatientAllergyRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public VisitPatientAllergyResponseDTO getVisitPatientAllergyById(Long id) {
        return visitPatientAllergyRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public VisitPatientAllergyResponseDTO createVisitPatientAllergy(VisitPatientAllergyRequestDTO dto) {
        VisitPatientAllergy allergy = toEntity(dto);
        VisitPatientAllergy saved = visitPatientAllergyRepository.save(allergy);
        return toResponseDTO(saved);
    }

    @Transactional
    public VisitPatientAllergyResponseDTO updateVisitPatientAllergy(Long id, VisitPatientAllergyRequestDTO dto) {
        VisitPatientAllergy allergy = visitPatientAllergyRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.ALLERGY_NOT_FOUND));
        allergy.setAllergyName(dto.getAllergyName());
        allergy.setReaction(dto.getReaction());
        allergy.setSeverity(dto.getSeverity());
        allergy.setNotes(dto.getNotes());
        VisitPatientAllergy updated = visitPatientAllergyRepository.save(allergy);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteVisitPatientAllergy(Long id) {
        visitPatientAllergyRepository.deleteById(id);
    }
}
