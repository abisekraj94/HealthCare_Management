package com.healthcare.mgnt;

import com.healthcare.mgnt.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootTest
public class ProductApplicationTests {
    @TestConfiguration
    static class SecurityTestConfig {
        @Bean
        public AuthenticationManager authenticationManager() {
            return authentication -> authentication;
        }
        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
            return org.mockito.Mockito.mock(JwtAuthenticationFilter.class);
        }
    }
    @Test
    void contextLoads() {
    }
}
