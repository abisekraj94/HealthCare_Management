package com.healthcare.mgnt.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig sets up the security configuration for the HealthCare Management System.
 * This class configures authentication, authorization rules, session management, and password encoding.
 * It also exposes beans for the authentication manager and password encoder used throughout the application.
 */

@Configuration
@EnableWebSecurity
@Profile("!test")
public class SecurityConfig {
    /**
     * Configures the main security filter chain for HTTP requests.
     * - Disables CSRF protection (for stateless REST APIs).
     * - Sets session management to stateless (no server-side sessions).
     * - Allows unauthenticated access to /api/auth/** endpoints.
     * - Requires authentication for all other endpoints.
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain bean
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );
        // Add JWT filter here if needed
        return http.build();
    }

    /**
     * Exposes the AuthenticationManager bean for use in authentication processes.
     * @param authenticationConfiguration the authentication configuration
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs during bean creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides a password encoder bean using BCrypt hashing algorithm.
     * This encoder is used to securely hash and verify user passwords.
     * @return the PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
