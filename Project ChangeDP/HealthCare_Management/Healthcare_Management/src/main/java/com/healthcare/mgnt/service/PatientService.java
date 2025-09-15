package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.PatientRequestDTO;
import com.healthcare.mgnt.dto.PatientResponseDTO;
import com.healthcare.mgnt.entity.Patient;
import com.healthcare.mgnt.repository.PatientRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    private Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setMrn(dto.getMrn());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setDob(dto.getDob());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        // You may need to set primaryPhysician/referralPhysician by ID lookup if needed
        return patient;
    }

    private PatientResponseDTO toResponseDTO(Patient entity) {
        if (entity == null) return null;
        PatientResponseDTO dto = new PatientResponseDTO();
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

    public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public PatientResponseDTO getPatientById(Long id) {
        return patientRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        Patient patient = toEntity(dto);
        Patient saved = patientRepository.save(patient);
        return toResponseDTO(saved);
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.PATIENT_NOT_FOUND));
        patient.setMrn(dto.getMrn());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setDob(dto.getDob());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        // You may need to set primaryPhysician/referralPhysician by ID lookup if needed
        Patient updated = patientRepository.save(patient);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
