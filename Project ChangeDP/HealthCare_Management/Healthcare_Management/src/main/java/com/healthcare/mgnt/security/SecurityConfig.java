package com.healthcare.mgnt.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfig sets up the security configuration for the HealthCare Management System.
 * This class configures authentication, authorization rules, session management, and password encoding.
 * It also exposes beans for the authentication manager and password encoder used throughout the application.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the main security filter chain for HTTP requests.
     * - Disables CSRF protection (for stateless REST APIs).
     * - Sets session management to stateless (no server-side sessions).
     * - Allows unauthenticated access to /api/auth/** and /api/v1/users/health endpoints.
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
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/swagger-ui/index.html",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/v3/api-docs/swagger-config",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**", "/api/v1/users/health").permitAll()
                .anyRequest().authenticated()
            )
            // Only add JWT filter for authenticated requests, not for Swagger/OpenAPI endpoints
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(withDefaults());
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

    /**
     * Configures CORS settings for the application.
     * Allows all origins, methods, and headers for Swagger/OpenAPI endpoints.
     * @return the WebMvcConfigurer bean
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    /**
     * Registers the JwtAuthenticationFilter as a bean.
     * This filter is used for authenticating requests with JWT tokens.
     * @param jwtUtil the JwtUtil component for token operations
     * @param userDetailsService the UserDetailsService for loading user-specific data
     * @return the JwtAuthenticationFilter bean
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }
}
