package com.healthcare.mgnt.service.Implementation.user;

import com.healthcare.mgnt.dto.request.UserRequest;
import com.healthcare.mgnt.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing user accounts in the healthcare system (e.g., staff, administrators).
 * Provides CRUD operations and paginated retrieval of user records.
 */
public interface IUserService {
    /**
     * Retrieves a paginated list of all users.
     * @param pageable pagination information
     * @return paginated list of user response DTOs
     */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Retrieves a user by their unique ID.
     * @param id user ID
     * @return user response DTO
     */
    UserResponse getUserById(Long id);

    /**
     * Creates a new user account.
     * @param userRequest user request DTO
     * @return created user response DTO
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Updates an existing user account.
     * @param id user ID
     * @param userRequest user request DTO with updated details
     * @return updated user response DTO
     */
    UserResponse updateUser(Long id, UserRequest userRequest);

    /**
     * Deletes a user account by its unique ID.
     * @param id user ID
     */
    void deleteUser(Long id);
}
