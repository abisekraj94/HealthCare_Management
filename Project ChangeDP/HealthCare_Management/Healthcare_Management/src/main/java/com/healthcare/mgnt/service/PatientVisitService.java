package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientVisitRequestDTO;
import com.healthcare.mgnt.dto.PatientVisitResponseDTO;
import com.healthcare.mgnt.entity.PatientVisit;
import com.healthcare.mgnt.repository.PatientVisitRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientVisitService {
    @Autowired
    private PatientVisitRepository patientVisitRepository;

    private PatientVisit toEntity(PatientVisitRequestDTO dto) {
        PatientVisit visit = new PatientVisit();
        // You may need to set patient/physician by ID lookup if needed
        visit.setVisitDate(dto.getVisitDate());
        visit.setVisitType(dto.getVisitType());
        visit.setNotes(dto.getNotes());
        return visit;
    }

    private PatientVisitResponseDTO toResponseDTO(PatientVisit entity) {
        if (entity == null) return null;
        PatientVisitResponseDTO dto = new PatientVisitResponseDTO();
        dto.setVisitId(entity.getVisitId());
        // You may need to set patientId/physicianId if needed
        dto.setVisitDate(entity.getVisitDate());
        dto.setVisitType(entity.getVisitType());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    public Page<PatientVisitResponseDTO> getAllPatientVisits(Pageable pageable) {
        return patientVisitRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public PatientVisitResponseDTO getPatientVisitById(Long id) {
        return patientVisitRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public PatientVisitResponseDTO createPatientVisit(PatientVisitRequestDTO dto) {
        PatientVisit visit = toEntity(dto);
        PatientVisit saved = patientVisitRepository.save(visit);
        return toResponseDTO(saved);
    }

    @Transactional
    public PatientVisitResponseDTO updatePatientVisit(Long id, PatientVisitRequestDTO dto) {
        PatientVisit visit = patientVisitRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.VISIT_NOT_FOUND));
        visit.setVisitDate(dto.getVisitDate());
        visit.setVisitType(dto.getVisitType());
        visit.setNotes(dto.getNotes());
        PatientVisit updated = patientVisitRepository.save(visit);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deletePatientVisit(Long id) {
        patientVisitRepository.deleteById(id);
    }
}
