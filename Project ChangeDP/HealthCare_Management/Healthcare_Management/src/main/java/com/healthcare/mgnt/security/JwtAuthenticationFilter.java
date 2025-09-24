package com.healthcare.mgnt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.healthcare.mgnt.exception.AuthException;

/**
 * JwtAuthenticationFilter is a Spring Security filter that intercepts HTTP requests
 * to validate JWT tokens and set the authentication context for the current user.
 * <p>
 * This filter extracts the JWT token from the Authorization header, validates it,
 * and sets the authenticated user in the SecurityContext if the token is valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to authenticate users based on JWT tokens.
     * <p>
     * If a valid JWT token is found in the Authorization header, the corresponding
     * user is loaded and the authentication context is set for the request.
     *
     * @param request      the incoming HTTP request
     * @param response     the HTTP response
     * @param filterChain  the filter chain to continue processing
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract JWT token from Authorization header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            username = jwtUtil.getEmailFromToken(token);
        }
        // Validate token and set authentication context if valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            try {
                jwtUtil.validateToken(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (AuthException e) {
                // Token is invalid or expired, do not set authentication
            }
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
