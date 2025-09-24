package com.healthcare.mgnt.controller.user;

import com.healthcare.mgnt.dto.request.UserRequest;
import com.healthcare.mgnt.dto.response.UserResponse;
import com.healthcare.mgnt.constants.ApplicationConstants;
import com.healthcare.mgnt.service.Implementation.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * Controller for user management operations.
 * Handles user CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * Get all users (paginated).
     */
    @Operation(summary = "Get all users", description = "Returns a paginated list of users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all users, page: {}, size: {}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return userService.getAllUsers(pageable);}

    /**
     * Get user by ID.
     */
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "404", description = ApplicationConstants.USER_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id: {}", id);
            UserResponse user = userService.getUserById(id);
            if (user == null) {
                logger.error("User not found for id: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
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
    public UserResponse createUser(@Validated @RequestBody UserRequest userRequest) {
        logger.info("Creating new user: {}", userRequest.getUsername());
        return userService.createUser(userRequest);
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
    public UserResponse updateUser(@PathVariable Long id, @Validated @RequestBody UserRequest userRequest) {
        logger.info("Updating user id: {}", id);
            return userService.updateUser(id, userRequest);
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
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user id: {}", id);
        userService.deleteUser(id);
    }
}
