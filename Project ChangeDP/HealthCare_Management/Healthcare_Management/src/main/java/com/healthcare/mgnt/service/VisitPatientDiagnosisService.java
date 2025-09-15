package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.VisitPatientDiagnosisRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientDiagnosisResponseDTO;
import com.healthcare.mgnt.entity.VisitPatientDiagnosis;
import com.healthcare.mgnt.repository.VisitPatientDiagnosisRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitPatientDiagnosisService {
    @Autowired
    private VisitPatientDiagnosisRepository visitPatientDiagnosisRepository;

    private VisitPatientDiagnosis toEntity(VisitPatientDiagnosisRequestDTO dto) {
        VisitPatientDiagnosis diagnosis = new VisitPatientDiagnosis();
        // You may need to set visit/diagnosedBy by ID lookup if needed
        diagnosis.setDiagnosisCode(dto.getDiagnosisCode());
        diagnosis.setDiagnosisDescription(dto.getDiagnosisDescription());
        diagnosis.setDiagnosedAt(dto.getDiagnosedAt());
        return diagnosis;
    }

    private VisitPatientDiagnosisResponseDTO toResponseDTO(VisitPatientDiagnosis entity) {
        if (entity == null) return null;
        VisitPatientDiagnosisResponseDTO dto = new VisitPatientDiagnosisResponseDTO();
        dto.setDiagnosisId(entity.getDiagnosisId());
        // You may need to set visitId/diagnosedById if needed
        dto.setDiagnosisCode(entity.getDiagnosisCode());
        dto.setDiagnosisDescription(entity.getDiagnosisDescription());
        dto.setDiagnosedAt(entity.getDiagnosedAt());
        return dto;
    }

    public Page<VisitPatientDiagnosisResponseDTO> getAllVisitPatientDiagnoses(Pageable pageable) {
        return visitPatientDiagnosisRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public VisitPatientDiagnosisResponseDTO getVisitPatientDiagnosisById(Long id) {
        return visitPatientDiagnosisRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public VisitPatientDiagnosisResponseDTO createVisitPatientDiagnosis(VisitPatientDiagnosisRequestDTO dto) {
        VisitPatientDiagnosis diagnosis = toEntity(dto);
        VisitPatientDiagnosis saved = visitPatientDiagnosisRepository.save(diagnosis);
        return toResponseDTO(saved);
    }

    @Transactional
    public VisitPatientDiagnosisResponseDTO updateVisitPatientDiagnosis(Long id, VisitPatientDiagnosisRequestDTO dto) {
        VisitPatientDiagnosis diagnosis = visitPatientDiagnosisRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.DIAGNOSIS_NOT_FOUND));
        diagnosis.setDiagnosisCode(dto.getDiagnosisCode());
        diagnosis.setDiagnosisDescription(dto.getDiagnosisDescription());
        diagnosis.setDiagnosedAt(dto.getDiagnosedAt());
        VisitPatientDiagnosis updated = visitPatientDiagnosisRepository.save(diagnosis);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteVisitPatientDiagnosis(Long id) {
        visitPatientDiagnosisRepository.deleteById(id);
    }
}
