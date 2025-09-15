package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.UserRequestDTO;
import com.healthcare.mgnt.dto.UserResponseDTO;
import com.healthcare.mgnt.entity.User;
import com.healthcare.mgnt.exception.UserNotFoundException;
import com.healthcare.mgnt.repository.UserRepository;
import com.healthcare.mgnt.repository.UserService;
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
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        logger.info("Fetching all users");
        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        logger.info("Creating new user: {}", userRequestDTO.getUsername());
        User user = modelMapper.map(userRequestDTO, User.class);
        // Hash password, set defaults, etc. (add logic as needed)
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        logger.info("Updating user id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        modelMapper.map(userRequestDTO, user); // Update fields
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}

