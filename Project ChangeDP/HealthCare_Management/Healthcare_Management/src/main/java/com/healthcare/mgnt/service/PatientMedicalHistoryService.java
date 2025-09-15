package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientMedicalHistoryRequestDTO;
import com.healthcare.mgnt.dto.PatientMedicalHistoryResponseDTO;
import com.healthcare.mgnt.entity.PatientMedicalHistory;
import com.healthcare.mgnt.repository.PatientMedicalHistoryRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientMedicalHistoryService {
    @Autowired
    private PatientMedicalHistoryRepository patientMedicalHistoryRepository;

    private PatientMedicalHistory toEntity(PatientMedicalHistoryRequestDTO dto) {
        PatientMedicalHistory history = new PatientMedicalHistory();
        // You may need to set patient by ID lookup if needed
        history.setCondition(dto.getCondition());
        history.setDescription(dto.getDescription());
        history.setDiagnosedAt(dto.getDiagnosedAt());
        history.setResolvedAt(dto.getResolvedAt());
        return history;
    }

    private PatientMedicalHistoryResponseDTO toResponseDTO(PatientMedicalHistory entity) {
        if (entity == null) return null;
        PatientMedicalHistoryResponseDTO dto = new PatientMedicalHistoryResponseDTO();
        dto.setHistoryId(entity.getHistoryId());
        // You may need to set patientId if needed
        dto.setCondition(entity.getCondition());
        dto.setDescription(entity.getDescription());
        dto.setDiagnosedAt(entity.getDiagnosedAt());
        dto.setResolvedAt(entity.getResolvedAt());
        return dto;
    }

    public Page<PatientMedicalHistoryResponseDTO> getAllPatientMedicalHistories(Pageable pageable) {
        return patientMedicalHistoryRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public PatientMedicalHistoryResponseDTO getPatientMedicalHistoryById(Long id) {
        return patientMedicalHistoryRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public PatientMedicalHistoryResponseDTO createPatientMedicalHistory(PatientMedicalHistoryRequestDTO dto) {
        PatientMedicalHistory history = toEntity(dto);
        PatientMedicalHistory saved = patientMedicalHistoryRepository.save(history);
        return toResponseDTO(saved);
    }

    @Transactional
    public PatientMedicalHistoryResponseDTO updatePatientMedicalHistory(Long id, PatientMedicalHistoryRequestDTO dto) {
        PatientMedicalHistory history = patientMedicalHistoryRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.MEDICAL_HISTORY_NOT_FOUND));
        history.setCondition(dto.getCondition());
        history.setDescription(dto.getDescription());
        history.setDiagnosedAt(dto.getDiagnosedAt());
        history.setResolvedAt(dto.getResolvedAt());
        PatientMedicalHistory updated = patientMedicalHistoryRepository.save(history);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deletePatientMedicalHistory(Long id) {
        patientMedicalHistoryRepository.deleteById(id);
    }
}
