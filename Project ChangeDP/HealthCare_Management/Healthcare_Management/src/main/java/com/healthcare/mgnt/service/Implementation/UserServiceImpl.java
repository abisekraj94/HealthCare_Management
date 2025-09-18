package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.UserRequest;
import com.healthcare.mgnt.dto.UserResponse;
import com.healthcare.mgnt.entity.User;
import com.healthcare.mgnt.exception.UserNotFoundException;
import com.healthcare.mgnt.repository.UserRepository;
import com.healthcare.mgnt.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for user operations in HealthCare Management System.
 * Handles business logic, DTO conversion, and exception handling.
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users");
        try {
            return userRepository.findAll(pageable)
                    .map(user -> modelMapper.map(user, UserResponse.class));
        } catch (Exception ex) {
            logger.error("Error fetching users: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch users");
        }
    }

    @Override
    public UserResponse getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);
            return modelMapper.map(user, UserResponse.class);
        } catch (UserNotFoundException ex) {
            logger.warn("User not found for id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error fetching user by id {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch user");
        }
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        logger.info("Creating new user: {}", userRequest.getUsername());
        try {
            User user = modelMapper.map(userRequest, User.class);
            User saved = userRepository.save(user);
            logger.info("User created with id: {}", saved.getUserId());
            return modelMapper.map(saved, UserResponse.class);
        } catch (Exception ex) {
            logger.error("Error creating user: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to create user");
        }
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        logger.info("Updating user id: {}", id);
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);
            modelMapper.map(userRequest, user); // Update fields
            User updatedUser = userRepository.save(user);
            logger.info("User updated with id: {}", updatedUser.getUserId());
            return modelMapper.map(updatedUser, UserResponse.class);
        } catch (UserNotFoundException ex) {
            logger.warn("User not found for update id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating user id {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Failed to update user");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user id: {}", id);
        try {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException();
            }
            userRepository.deleteById(id);
            logger.info("User deleted with id: {}", id);
        } catch (UserNotFoundException ex) {
            logger.warn("User not found for deletion id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting user id {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete user");
        }
    }
}
