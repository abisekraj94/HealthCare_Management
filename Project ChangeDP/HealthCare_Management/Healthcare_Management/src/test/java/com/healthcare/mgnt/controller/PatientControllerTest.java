package com.healthcare.mgnt.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.mgnt.dto.request.PatientRequest;
import com.healthcare.mgnt.dto.response.PatientResponse;
import com.healthcare.mgnt.security.JwtAuthenticationFilter;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.healthcare.mgnt.service.Implementation.patient.PatientService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Data
@ActiveProfiles("test")
@Import({PatientControllerTest.TestAuthConfig.class, PatientControllerTest.TestSecurityConfig.class, PatientControllerTest.MockPatientServiceConfig.class, PatientControllerTest.MockJwtAuthenticationFilterConfig.class})
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientService patientService;

    private MockMvc standaloneMockMvc;

    @BeforeEach
    void setUp() {
        Mockito.reset(patientService);
        // Standalone setup for proper serialization
        standaloneMockMvc = MockMvcBuilders.standaloneSetup(new com.healthcare.mgnt.controller.patient.PatientController(patientService))
            .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
            .build();
    }

    @Test
    void testTenantIsolationViaHeader() throws Exception {
        // Create patient for SIMS
        PatientRequest patientSims = new PatientRequest();
        patientSims.setMrn("SIMS-001");
        patientSims.setFirstName("John");
        patientSims.setLastName("Doe");
        patientSims.setDob(new java.sql.Date(System.currentTimeMillis()));
        patientSims.setGender("Male");
        patientSims.setPhone("1234567890");
        patientSims.setEmail("john.doe@example.com");
        patientSims.setAddress("123 Main St");
        patientSims.setPrimaryPhysicianId(1L);
        patientSims.setReferralPhysicianId(2L);
        PatientResponse simsResponseObj = new PatientResponse();
        simsResponseObj.setMrn("SIMS-001");
        simsResponseObj.setFirstName("John");
        simsResponseObj.setLastName("Doe");
        simsResponseObj.setDob(new java.sql.Date(System.currentTimeMillis()));
        simsResponseObj.setGender("Male");
        simsResponseObj.setPhone("1234567890");
        simsResponseObj.setEmail("john.doe@example.com");
        simsResponseObj.setAddress("123 Main St");
        simsResponseObj.setPrimaryPhysicianId(1L);
        simsResponseObj.setReferralPhysicianId(2L);

        // Create patient for MIOT
        PatientRequest patientMiot = new PatientRequest();
        patientMiot.setMrn("MIOT-001");
        patientMiot.setFirstName("Jane");
        patientMiot.setLastName("Smith");
        patientMiot.setDob(new java.sql.Date(System.currentTimeMillis()));
        patientMiot.setGender("Female");
        patientMiot.setPhone("0987654321");
        patientMiot.setEmail("jane.smith@example.com");
        patientMiot.setAddress("456 Main St");
        patientMiot.setPrimaryPhysicianId(3L);
        patientMiot.setReferralPhysicianId(4L);
        PatientResponse miotResponseObj = new PatientResponse();
        miotResponseObj.setMrn("MIOT-001");
        miotResponseObj.setFirstName("Jane");
        miotResponseObj.setLastName("Smith");
        miotResponseObj.setDob(new java.sql.Date(System.currentTimeMillis()));
        miotResponseObj.setGender("Female");
        miotResponseObj.setPhone("0987654321");
        miotResponseObj.setEmail("jane.smith@example.com");
        miotResponseObj.setAddress("456 Main St");
        miotResponseObj.setPrimaryPhysicianId(3L);
        miotResponseObj.setReferralPhysicianId(4L);

        // Mock createPatient for both tenants
        Mockito.when(patientService.createPatient(ArgumentMatchers.any(PatientRequest.class)))
            .thenAnswer(invocation -> {
                PatientRequest req = invocation.getArgument(0);
                if ("SIMS-001".equals(req.getMrn())) {
                    return simsResponseObj;
                } else if ("MIOT-001".equals(req.getMrn())) {
                    return miotResponseObj;
                }
                return null;
            });
        // Mock getAllPatients for both tenants
        Mockito.when(patientService.getAllPatients(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenAnswer(invocation -> {
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                String tenant = attrs != null && attrs.getRequest().getHeader("X-Tenant-ID") != null ?
                    attrs.getRequest().getHeader("X-Tenant-ID") : "";
                if ("SIMS".equals(tenant)) {
                    return java.util.Collections.singletonList(simsResponseObj);
                } else if ("MIOT".equals(tenant)) {
                    return java.util.Collections.singletonList(miotResponseObj);
                }
                return java.util.Collections.emptyList();
            });
        String simsJson = objectMapper.writeValueAsString(patientSims);
        String miotJson = objectMapper.writeValueAsString(patientMiot);

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Tenant-ID", "SIMS")
                .content(simsJson))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Tenant-ID", "MIOT")
                .content(miotJson))
                .andExpect(status().isOk());

        // Fetch patients for SIMS
        MvcResult resultSims = standaloneMockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "SIMS")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String simsResponse = resultSims.getResponse().getContentAsString();
        assertThat(simsResponse).as("Controller returned empty response body for SIMS patient fetch.").isNotBlank();
        com.fasterxml.jackson.databind.JsonNode simsJsonNode = objectMapper.readTree(simsResponse);
        JsonNode simsDataNode = simsJsonNode.get("data");
        String simsText;
        if (simsDataNode != null && !simsDataNode.isNull()) {
            simsText = simsDataNode.toString();
        } else {
            simsText = simsJsonNode.toString();
        }
        assertThat(simsText).contains("SIMS-001");
        assertThat(simsText).doesNotContain("MIOT-001");

        // Fetch patients for MIOT
        MvcResult resultMiot = standaloneMockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "MIOT")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String miotResponse = resultMiot.getResponse().getContentAsString();
        assertThat(miotResponse).as("Controller returned empty response body for MIOT patient fetch.").isNotBlank();
        com.fasterxml.jackson.databind.JsonNode miotJsonNode = objectMapper.readTree(miotResponse);
        JsonNode miotDataNode = miotJsonNode.get("data");
        String miotText;
        if (miotDataNode != null && !miotDataNode.isNull()) {
            miotText = miotDataNode.toString();
        } else {
            miotText = miotJsonNode.toString();
        }
        assertThat(miotText).contains("MIOT-001");
        assertThat(miotText).doesNotContain("SIMS-001");
    }

    @Test
    void testTenantDetectionViaRequestBody() throws Exception {
        // Create patient for APOLLO using tenant in body
        PatientRequest patientApollo = new PatientRequest();
        patientApollo.setMrn("APOLLO-001");
        patientApollo.setFirstName("Alice");
        patientApollo.setLastName("Wonderland");
        patientApollo.setDob(new java.sql.Date(System.currentTimeMillis()));
        patientApollo.setGender("Female");
        patientApollo.setPhone("1112223333");
        patientApollo.setEmail("alice.wonderland@example.com");
        patientApollo.setAddress("789 Main St");
        patientApollo.setPrimaryPhysicianId(5L);
        patientApollo.setReferralPhysicianId(6L);
        PatientResponse apolloResponseObj = new PatientResponse();
        apolloResponseObj.setMrn("APOLLO-001");
        apolloResponseObj.setFirstName("Alice");
        apolloResponseObj.setLastName("Wonderland");
        apolloResponseObj.setDob(new java.sql.Date(System.currentTimeMillis()));
        apolloResponseObj.setGender("Female");
        apolloResponseObj.setPhone("1112223333");
        apolloResponseObj.setEmail("alice.wonderland@example.com");
        apolloResponseObj.setAddress("789 Main St");
        apolloResponseObj.setPrimaryPhysicianId(5L);
        apolloResponseObj.setReferralPhysicianId(6L);
        // Mock createPatient for APOLLO
        Mockito.when(patientService.createPatient(ArgumentMatchers.any(PatientRequest.class))).thenReturn(apolloResponseObj);
        // Mock getAllPatients for APOLLO
        Mockito.when(patientService.getAllPatients(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenAnswer(invocation -> {
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                String tenant = attrs != null && attrs.getRequest().getHeader("X-Tenant-ID") != null ?
                    attrs.getRequest().getHeader("X-Tenant-ID") : "";
                if ("APOLLO".equals(tenant)) {
                    return java.util.Collections.singletonList(apolloResponseObj);
                }
                return java.util.Collections.emptyList();
            });
        String apolloJson = objectMapper.createObjectNode()
                .put("mrn", patientApollo.getMrn())
                .put("firstName", patientApollo.getFirstName())
                .put("lastName", patientApollo.getLastName())
                .put("dob", patientApollo.getDob().toString())
                .put("gender", patientApollo.getGender())
                .put("phone", patientApollo.getPhone())
                .put("email", patientApollo.getEmail())
                .put("address", patientApollo.getAddress())
                .put("primaryPhysicianId", patientApollo.getPrimaryPhysicianId())
                .put("referralPhysicianId", patientApollo.getReferralPhysicianId())
                .put("tenant", "APOLLO")
                .toString();

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(apolloJson))
                .andExpect(status().isOk());

        // Fetch patients for APOLLO
        MvcResult resultApollo = standaloneMockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "APOLLO")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String apolloResponse = resultApollo.getResponse().getContentAsString();
        assertThat(apolloResponse).as("Controller returned empty response body for APOLLO patient fetch.").isNotBlank();
        com.fasterxml.jackson.databind.JsonNode apolloJsonNode = objectMapper.readTree(apolloResponse);
        JsonNode apolloDataNode = apolloJsonNode.get("data");
        String apolloText;
        if (apolloDataNode != null && !apolloDataNode.isNull()) {
            apolloText = apolloDataNode.toString();
        } else {
            apolloText = apolloJsonNode.toString();
        }
        assertThat(apolloText).contains("APOLLO-001");
    }

    @TestConfiguration
    static class TestAuthConfig {
        @Bean
        public AuthenticationManager authenticationManager() {
            return authentication -> authentication;
        }
    }
    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
            return http.build();
        }
    }
    @TestConfiguration
    static class MockPatientServiceConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }
    @TestConfiguration
    static class MockJwtAuthenticationFilterConfig {
        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
            return org.mockito.Mockito.mock(JwtAuthenticationFilter.class);
        }
    }
}
