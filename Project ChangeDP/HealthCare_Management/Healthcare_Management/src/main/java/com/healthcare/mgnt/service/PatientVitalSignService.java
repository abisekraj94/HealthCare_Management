package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientVitalSignRequestDTO;
import com.healthcare.mgnt.dto.PatientVitalSignResponseDTO;
import com.healthcare.mgnt.entity.PatientVitalSign;
import com.healthcare.mgnt.repository.PatientVitalSignRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientVitalSignService {
    @Autowired
    private PatientVitalSignRepository patientVitalSignRepository;

    private PatientVitalSign toEntity(PatientVitalSignRequestDTO dto) {
        PatientVitalSign vitalSign = new PatientVitalSign();
        // You may need to set patient by ID lookup if needed
        vitalSign.setRecordedAt(dto.getRecordedAt());
        vitalSign.setTemperature(dto.getTemperature());
        vitalSign.setBloodPressure(dto.getBloodPressure());
        vitalSign.setHeartRate(dto.getHeartRate());
        vitalSign.setRespiratoryRate(dto.getRespiratoryRate());
        vitalSign.setNotes(dto.getNotes());
        return vitalSign;
    }

    private PatientVitalSignResponseDTO toResponseDTO(PatientVitalSign entity) {
        if (entity == null) return null;
        PatientVitalSignResponseDTO dto = new PatientVitalSignResponseDTO();
        dto.setVitalSignId(entity.getVitalSignId());
        // You may need to set patientId if needed
        dto.setRecordedAt(entity.getRecordedAt());
        dto.setTemperature(entity.getTemperature());
        dto.setBloodPressure(entity.getBloodPressure());
        dto.setHeartRate(entity.getHeartRate());
        dto.setRespiratoryRate(entity.getRespiratoryRate());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    public Page<PatientVitalSignResponseDTO> getAllPatientVitalSigns(Pageable pageable) {
        return patientVitalSignRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public PatientVitalSignResponseDTO getPatientVitalSignById(Long id) {
        return patientVitalSignRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public PatientVitalSignResponseDTO createPatientVitalSign(PatientVitalSignRequestDTO dto) {
        PatientVitalSign vitalSign = toEntity(dto);
        PatientVitalSign saved = patientVitalSignRepository.save(vitalSign);
        return toResponseDTO(saved);
    }

    @Transactional
    public PatientVitalSignResponseDTO updatePatientVitalSign(Long id, PatientVitalSignRequestDTO dto) {
        PatientVitalSign vitalSign = patientVitalSignRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.VITAL_SIGN_NOT_FOUND));
        vitalSign.setRecordedAt(dto.getRecordedAt());
        vitalSign.setTemperature(dto.getTemperature());
        vitalSign.setBloodPressure(dto.getBloodPressure());
        vitalSign.setHeartRate(dto.getHeartRate());
        vitalSign.setRespiratoryRate(dto.getRespiratoryRate());
        vitalSign.setNotes(dto.getNotes());
        PatientVitalSign updated = patientVitalSignRepository.save(vitalSign);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deletePatientVitalSign(Long id) {
        patientVitalSignRepository.deleteById(id);
    }
}
