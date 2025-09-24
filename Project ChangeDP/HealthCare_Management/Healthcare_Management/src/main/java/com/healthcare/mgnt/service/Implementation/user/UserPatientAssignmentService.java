package com.healthcare.mgnt.service.Implementation.user;

import com.healthcare.mgnt.dto.request.UserPatientAssignmentRequest;
import com.healthcare.mgnt.dto.response.UserPatientAssignmentResponse;
import com.healthcare.mgnt.entity.user.UserPatientAssignment;
import com.healthcare.mgnt.repository.user.UserPatientAssignmentRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for user-patient assignment management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class UserPatientAssignmentService implements IUserPatientAssignmentService {
    private static final Logger logger = LoggerFactory.getLogger(UserPatientAssignmentService.class);
    @Autowired
    private UserPatientAssignmentRepository userPatientAssignmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts UserPatientAssignmentRequestDTO to UserPatientAssignment entity using ModelMapper.
     * @param dto UserPatientAssignmentRequestDTO
     * @return UserPatientAssignment entity
     */
    private UserPatientAssignment toEntity(UserPatientAssignmentRequest dto) {
        return modelMapper.map(dto, UserPatientAssignment.class);
    }

    /**
     * Converts UserPatientAssignment entity to UserPatientAssignmentResponseDTO using ModelMapper.
     * @param entity UserPatientAssignment entity
     * @return UserPatientAssignmentResponseDTO
     */
    private UserPatientAssignmentResponse toResponseDTO(UserPatientAssignment entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, UserPatientAssignmentResponse.class);
    }

    /**
     * Returns a paginated list of user-patient assignments.
     * @param pageable Pageable object
     * @return Page of UserPatientAssignmentResponseDTO
     */
    public Page<UserPatientAssignmentResponse> getAllUserPatientAssignments(Pageable pageable) {
        logger.info("Fetching all user-patient assignments with pageable: {}", pageable);
        try {
            return userPatientAssignmentRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching user-patient assignments: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Returns a user-patient assignment by ID.
     * @param id Assignment ID
     * @return UserPatientAssignmentResponseDTO or null if not found
     */
    public UserPatientAssignmentResponse getUserPatientAssignmentById(Long id) {
        logger.info("Fetching user-patient assignment by id: {}", id);
        try {
            return userPatientAssignmentRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching user-patient assignment by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Creates a new user-patient assignment.
     * @param dto UserPatientAssignmentRequestDTO
     * @return Created UserPatientAssignmentResponseDTO
     */
    @Transactional
    public UserPatientAssignmentResponse createUserPatientAssignment(UserPatientAssignmentRequest dto) {
        logger.info("Creating user-patient assignment: {}", dto);
        try {
            UserPatientAssignment assignment = toEntity(dto);
            UserPatientAssignment saved = userPatientAssignmentRepository.save(assignment);
            logger.info("User-patient assignment created with id: {}", saved.getAssignmentId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating user-patient assignment: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Updates an existing user-patient assignment.
     * @param id Assignment ID
     * @param dto UserPatientAssignmentRequestDTO
     * @return Updated UserPatientAssignmentResponseDTO
     */
    @Transactional
    public UserPatientAssignmentResponse updateUserPatientAssignment(Long id, UserPatientAssignmentRequest dto) {
        logger.info("Updating user-patient assignment with id {}: {}", id, dto);
        try {
            UserPatientAssignment assignment = userPatientAssignmentRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.ASSIGNMENT_NOT_FOUND));
            modelMapper.map(dto, assignment);
            UserPatientAssignment updated = userPatientAssignmentRepository.save(assignment);
            logger.info("User-patient assignment updated with id: {}", updated.getAssignmentId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("User-patient assignment not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating user-patient assignment id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Deletes a user-patient assignment by ID.
     * @param id Assignment ID
     */
    @Transactional
    public void deleteUserPatientAssignment(Long id) {
        logger.info("Deleting user-patient assignment with id: {}", id);
        try {
            if (!userPatientAssignmentRepository.existsById(id)) {
                logger.warn("User-patient assignment not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.ASSIGNMENT_NOT_FOUND);
            }
            userPatientAssignmentRepository.deleteById(id);
            logger.info("User-patient assignment deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting user-patient assignment id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }
}
