package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.constants.ApplicationConstants;
import com.healthcare.mgnt.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for authentication operations in the HealthCare Management System.
 * Provides REST endpoints for user login and JWT token generation to enable secure access to protected resources.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> authenticateUser(@RequestParam String username, @RequestParam String password) {
        logger.info("Authenticating user: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String email = principal.getUsername();
            java.util.List<String> roles = principal.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .toList();
            String jwt = jwtUtil.generateToken(null, email, roles);
            logger.info("Authentication successful for user: {}", username);
            return ResponseEntity.ok(jwt);
        } catch (Exception ex) {
            logger.error("Authentication failed for user {}: {}", username, ex.getMessage(), ex);
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
