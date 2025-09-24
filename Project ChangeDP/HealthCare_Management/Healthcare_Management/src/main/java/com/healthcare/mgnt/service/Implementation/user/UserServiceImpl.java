package com.healthcare.mgnt.service.Implementation.user;

import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.dto.request.UserRequest;
import com.healthcare.mgnt.dto.response.UserResponse;
import com.healthcare.mgnt.entity.user.User;
import com.healthcare.mgnt.exception.UserException;
import com.healthcare.mgnt.repository.user.UserRepository;
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
                    .orElseThrow(() -> new UserException(AppErrorCodes.USER_NOT_FOUND));
            return modelMapper.map(user, UserResponse.class);
        } catch (UserException ex) {
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
                    .orElseThrow(() -> new UserException(AppErrorCodes.USER_NOT_FOUND));
            modelMapper.map(userRequest, user); // Update fields
            User updatedUser = userRepository.save(user);
            logger.info("User updated with id: {}", updatedUser.getUserId());
            return modelMapper.map(updatedUser, UserResponse.class);
        } catch (Exception ex) {
            if (ex instanceof UserException) {
                logger.warn("User not found for update id: {}", id);
                throw ex;
            }
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
                throw new UserException(AppErrorCodes.USER_NOT_FOUND);
            }
            userRepository.deleteById(id);
            logger.info("User deleted with id: {}", id);
        } catch (UserException ex) {
            logger.warn("User not found for deletion id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting user id {}: {}", id, ex.getMessage(), ex);
            throw new RuntimeException("Failed to delete user");
        }
    }
}
