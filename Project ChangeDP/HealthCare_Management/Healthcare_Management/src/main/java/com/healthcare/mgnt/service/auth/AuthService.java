package com.healthcare.mgnt.service.auth;

import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.dto.request.AuthRequest;
import com.healthcare.mgnt.dto.response.AuthResponse;
import com.healthcare.mgnt.exception.AuthException;
import com.healthcare.mgnt.exception.UserException;
import com.healthcare.mgnt.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            logger.info("Authenticating user: {}", authRequest.getUserName());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) authentication.getPrincipal();
            String email = principal.getUsername();
            List<String> roles = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            String jwtToken = jwtUtil.generateToken(null, email, roles);
            logger.info("Authentication successful for user: {}", authRequest.getUserName());
            return new AuthResponse(jwtToken);
        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for user {}: {}", authRequest.getUserName(), ex.getMessage(), ex);
            throw new AuthException(AppErrorCodes.INVALID_USER_CREDENTIALS);
        } catch (Exception ex) {
            logger.error("Authentication failed for user {}: {}", authRequest.getUserName(), ex.getMessage(), ex);
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
