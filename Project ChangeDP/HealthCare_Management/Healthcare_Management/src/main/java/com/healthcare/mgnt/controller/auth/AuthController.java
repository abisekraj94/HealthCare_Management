package com.healthcare.mgnt.controller.auth;

import com.healthcare.mgnt.constants.ApplicationConstants;
import com.healthcare.mgnt.dto.common.BaseResponse;
import com.healthcare.mgnt.dto.request.AuthRequest;
import com.healthcare.mgnt.dto.response.AuthResponse;
import com.healthcare.mgnt.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication operations in the HealthCare Management System.
 * Provides REST endpoints for user login and JWT token generation to enable secure access to protected resources.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Authenticate user and return JWT token.
     */
    @Operation(summary = "Authenticate user", description = "Authenticates user and returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
            @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED),
            @ApiResponse(responseCode = "500", description = ApplicationConstants.INTERNAL_ERROR)
    })
    @PostMapping("/login")
    public BaseResponse<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        return new BaseResponse<>(true, "Success", authService.authenticate(authRequest));
    }
}
