package com.healthcare.mgnt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.mgnt.dto.PatientRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testTenantIsolationViaHeader() throws Exception {
        // Create patient for SIMS
        PatientRequestDTO patientSims = new PatientRequestDTO();
        patientSims.setMrn("SIMS-001");
        patientSims.setFirstName("John");
        patientSims.setLastName("Doe");
        String simsJson = objectMapper.writeValueAsString(patientSims);

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Tenant-ID", "SIMS")
                .content(simsJson))
                .andExpect(status().isCreated());

        // Create patient for MIOT
        PatientRequestDTO patientMiot = new PatientRequestDTO();
        patientMiot.setMrn("MIOT-001");
        patientMiot.setFirstName("Jane");
        patientMiot.setLastName("Smith");
        String miotJson = objectMapper.writeValueAsString(patientMiot);

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Tenant-ID", "MIOT")
                .content(miotJson))
                .andExpect(status().isCreated());

        // Fetch patients for SIMS
        MvcResult resultSims = mockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "SIMS"))
                .andExpect(status().isOk())
                .andReturn();
        String simsResponse = resultSims.getResponse().getContentAsString();
        assertThat(simsResponse).contains("SIMS-001");
        assertThat(simsResponse).doesNotContain("MIOT-001");

        // Fetch patients for MIOT
        MvcResult resultMiot = mockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "MIOT"))
                .andExpect(status().isOk())
                .andReturn();
        String miotResponse = resultMiot.getResponse().getContentAsString();
        assertThat(miotResponse).contains("MIOT-001");
        assertThat(miotResponse).doesNotContain("SIMS-001");
    }

    @Test
    void testTenantDetectionViaRequestBody() throws Exception {
        // Create patient for APOLLO using tenant in body
        PatientRequestDTO patientApollo = new PatientRequestDTO();
        patientApollo.setMrn("APOLLO-001");
        patientApollo.setFirstName("Alice");
        patientApollo.setLastName("Wonderland");
        String apolloJson = objectMapper.createObjectNode()
                .put("mrn", patientApollo.getMrn())
                .put("firstName", patientApollo.getFirstName())
                .put("lastName", patientApollo.getLastName())
                .put("tenant", "APOLLO")
                .toString();

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(apolloJson))
                .andExpect(status().isCreated());

        // Fetch patients for APOLLO
        MvcResult resultApollo = mockMvc.perform(get("/api/patients")
                .header("X-Tenant-ID", "APOLLO"))
                .andExpect(status().isOk())
                .andReturn();
        String apolloResponse = resultApollo.getResponse().getContentAsString();
        assertThat(apolloResponse).contains("APOLLO-001");
    }
}
