package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.UserPatientAssignmentRequestDTO;
import com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO;
import com.healthcare.mgnt.entity.UserPatientAssignment;
import com.healthcare.mgnt.repository.UserPatientAssignmentRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPatientAssignmentService {
    @Autowired
    private UserPatientAssignmentRepository userPatientAssignmentRepository;

    private UserPatientAssignment toEntity(UserPatientAssignmentRequestDTO dto) {
        UserPatientAssignment assignment = new UserPatientAssignment();
        // You may need to set user/patient by ID lookup if needed
        assignment.setAssignmentType(dto.getAssignmentType());
        assignment.setAssignedAt(dto.getAssignedAt());
        assignment.setUnassignedAt(dto.getUnassignedAt());
        return assignment;
    }

    private UserPatientAssignmentResponseDTO toResponseDTO(UserPatientAssignment entity) {
        if (entity == null) return null;
        UserPatientAssignmentResponseDTO dto = new UserPatientAssignmentResponseDTO();
        dto.setAssignmentId(entity.getAssignmentId());
        // You may need to set userId/patientId if needed
        dto.setAssignmentType(entity.getAssignmentType());
        dto.setAssignedAt(entity.getAssignedAt());
        dto.setUnassignedAt(entity.getUnassignedAt());
        return dto;
    }

    public Page<com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO> getAllUserPatientAssignments(Pageable pageable) {
        return userPatientAssignmentRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO getUserPatientAssignmentById(Long id) {
        return userPatientAssignmentRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO createUserPatientAssignment(UserPatientAssignmentRequestDTO dto) {
        UserPatientAssignment assignment = toEntity(dto);
        UserPatientAssignment saved = userPatientAssignmentRepository.save(assignment);
        return toResponseDTO(saved);
    }

    @Transactional
    public com.healthcare.mgnt.dto.UserPatientAssignmentResponseDTO updateUserPatientAssignment(Long id, UserPatientAssignmentRequestDTO dto) {
        UserPatientAssignment assignment = userPatientAssignmentRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.ASSIGNMENT_NOT_FOUND));
        assignment.setAssignmentType(dto.getAssignmentType());
        assignment.setAssignedAt(dto.getAssignedAt());
        assignment.setUnassignedAt(dto.getUnassignedAt());
        UserPatientAssignment updated = userPatientAssignmentRepository.save(assignment);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteUserPatientAssignment(Long id) {
        userPatientAssignmentRepository.deleteById(id);
    }
}
