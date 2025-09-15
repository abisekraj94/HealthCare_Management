package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.dto.UserRequestDTO;
import com.healthcare.mgnt.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for user operations in HealthCare Management System.
 */
public interface UserService {
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}

