package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.UserPatientAssignmentRequest;
import com.healthcare.mgnt.dto.UserPatientAssignmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing assignments between users (e.g., doctors, nurses) and patients.
 * Provides CRUD operations and paginated retrieval of user-patient assignment records.
 */
public interface IUserPatientAssignmentService {
    /**
     * Retrieves a paginated list of all user-patient assignments.
     * @param pageable pagination information
     * @return paginated list of user-patient assignment response DTOs
     */
    Page<UserPatientAssignmentResponse> getAllUserPatientAssignments(Pageable pageable);

    /**
     * Retrieves a user-patient assignment by its unique ID.
     * @param id assignment ID
     * @return user-patient assignment response DTO
     */
    UserPatientAssignmentResponse getUserPatientAssignmentById(Long id);

    /**
     * Creates a new user-patient assignment record.
     * @param dto user-patient assignment request DTO
     * @return created user-patient assignment response DTO
     */
    UserPatientAssignmentResponse createUserPatientAssignment(UserPatientAssignmentRequest dto);

    /**
     * Updates an existing user-patient assignment record.
     * @param id assignment ID
     * @param dto user-patient assignment request DTO with updated details
     * @return updated user-patient assignment response DTO
     */
    UserPatientAssignmentResponse updateUserPatientAssignment(Long id, UserPatientAssignmentRequest dto);

    /**
     * Deletes a user-patient assignment by its unique ID.
     * @param id assignment ID
     */
    void deleteUserPatientAssignment(Long id);
}
