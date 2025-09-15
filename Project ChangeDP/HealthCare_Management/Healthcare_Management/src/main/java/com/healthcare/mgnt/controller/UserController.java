package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.UserRequestDTO;
import com.healthcare.mgnt.dto.UserResponseDTO;
import com.healthcare.mgnt.repository.UserService;
import com.healthcare.mgnt.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user management operations.
 * Handles user CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Get all users (paginated).
     */
    @Operation(summary = "Get all users", description = "Returns a paginated list of users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all users, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID.
     */
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id: {}", id);
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Create a new user.
     */
    @Operation(summary = "Create a new user", description = "Creates a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
            @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Validated @RequestBody UserRequestDTO userRequestDTO) {
        logger.info("Creating new user: {}", userRequestDTO.getUsername());
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    /**
     * Update an existing user.
     */
    @Operation(summary = "Update user", description = "Updates an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_NOT_FOUND),
            @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Validated @RequestBody UserRequestDTO userRequestDTO) {
        logger.info("Updating user id: {}", id);
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user by ID.
     */
    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
