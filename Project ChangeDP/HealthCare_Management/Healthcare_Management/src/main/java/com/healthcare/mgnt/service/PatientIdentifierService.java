package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientIdentifierRequestDTO;
import com.healthcare.mgnt.dto.PatientIdentifierResponseDTO;
import com.healthcare.mgnt.entity.PatientIdentifier;
import com.healthcare.mgnt.repository.PatientIdentifierRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientIdentifierService {
    @Autowired
    private PatientIdentifierRepository patientIdentifierRepository;

    private PatientIdentifier toEntity(PatientIdentifierRequestDTO dto) {
        PatientIdentifier identifier = new PatientIdentifier();
        identifier.setIdentifierType(dto.getIdentifierType());
        identifier.setIdentifierValue(dto.getIdentifierValue());
        // You may need to set patient by ID lookup if needed
        return identifier;
    }

    private PatientIdentifierResponseDTO toResponseDTO(PatientIdentifier entity) {
        if (entity == null) return null;
        PatientIdentifierResponseDTO dto = new PatientIdentifierResponseDTO();
        dto.setIdentifierId(entity.getIdentifierId());
        dto.setIdentifierType(entity.getIdentifierType());
        dto.setIdentifierValue(entity.getIdentifierValue());
        // You may need to set patientId if needed
        return dto;
    }

    public Page<PatientIdentifierResponseDTO> getAllPatientIdentifiers(Pageable pageable) {
        return patientIdentifierRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public PatientIdentifierResponseDTO getPatientIdentifierById(Long id) {
        return patientIdentifierRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public PatientIdentifierResponseDTO createPatientIdentifier(PatientIdentifierRequestDTO dto) {
        PatientIdentifier identifier = toEntity(dto);
        PatientIdentifier saved = patientIdentifierRepository.save(identifier);
        return toResponseDTO(saved);
    }

    @Transactional
    public PatientIdentifierResponseDTO updatePatientIdentifier(Long id, PatientIdentifierRequestDTO dto) {
        PatientIdentifier identifier = patientIdentifierRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.IDENTIFIER_NOT_FOUND));
        identifier.setIdentifierType(dto.getIdentifierType());
        identifier.setIdentifierValue(dto.getIdentifierValue());
        // You may need to set patient by ID lookup if needed
        PatientIdentifier updated = patientIdentifierRepository.save(identifier);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deletePatientIdentifier(Long id) {
        patientIdentifierRepository.deleteById(id);
    }
}
