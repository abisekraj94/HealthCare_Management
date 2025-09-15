package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.VisitPatientMedicationRequestDTO;
import com.healthcare.mgnt.dto.VisitPatientMedicationResponseDTO;
import com.healthcare.mgnt.entity.VisitPatientMedication;
import com.healthcare.mgnt.entity.PatientVisit;
import com.healthcare.mgnt.entity.User;
import com.healthcare.mgnt.repository.VisitPatientMedicationRepository;
import com.healthcare.mgnt.repository.PatientVisitRepository;
import com.healthcare.mgnt.repository.UserRepository;
import com.healthcare.mgnt.exception.MedicationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VisitPatientMedicationServiceTest {
    @Mock
    private VisitPatientMedicationRepository medicationRepository;
    @Mock
    private PatientVisitRepository patientVisitRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VisitPatientMedicationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVisitPatientMedications() {
        VisitPatientMedication med = new VisitPatientMedication();
        med.setMedicationId(1L);
        med.setMedicationName("Aspirin");
        med.setDosage("100mg");
        med.setFrequency("Once daily");
        med.setRoute("Oral");
        med.setStartDate(Date.valueOf("2023-01-01"));
        med.setEndDate(Date.valueOf("2023-01-10"));
        med.setVisit(new PatientVisit());
        med.setPrescribedBy(new User());
        Page<VisitPatientMedication> page = new PageImpl<>(Collections.singletonList(med));
        when(medicationRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<VisitPatientMedicationResponseDTO> result = service.getAllVisitPatientMedications(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("Aspirin", result.getContent().get(0).getMedicationName());
    }

    @Test
    void testGetVisitPatientMedicationById_Success() {
        VisitPatientMedication med = new VisitPatientMedication();
        med.setMedicationId(2L);
        med.setMedicationName("Ibuprofen");
        when(medicationRepository.findById(2L)).thenReturn(Optional.of(med));
        VisitPatientMedicationResponseDTO dto = service.getVisitPatientMedicationById(2L);
        assertEquals("Ibuprofen", dto.getMedicationName());
        assertEquals(2L, dto.getMedicationId());
    }

    @Test
    void testGetVisitPatientMedicationById_NotFound() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MedicationNotFoundException.class, () -> service.getVisitPatientMedicationById(99L));
    }

    @Test
    void testCreateVisitPatientMedication_Success() {
        VisitPatientMedicationRequestDTO req = new VisitPatientMedicationRequestDTO();
        req.setVisitId(1L);
        req.setMedicationName("Paracetamol");
        req.setDosage("500mg");
        req.setFrequency("Twice daily");
        req.setRoute("Oral");
        req.setStartDate(Date.valueOf("2023-02-01"));
        req.setEndDate(Date.valueOf("2023-02-05"));
        req.setPrescribedById(10L);
        PatientVisit visit = new PatientVisit();
        visit.setVisitId(1L);
        User user = new User();
        user.setUserId(10L);
        when(patientVisitRepository.findById(1L)).thenReturn(Optional.of(visit));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(medicationRepository.save(any(VisitPatientMedication.class))).thenAnswer(i -> i.getArgument(0));
        VisitPatientMedicationResponseDTO dto = service.createVisitPatientMedication(req);
        assertEquals("Paracetamol", dto.getMedicationName());
        assertEquals("Twice daily", dto.getFrequency());
        assertEquals(1L, dto.getVisitId());
        assertEquals(10L, dto.getPrescribedById());
    }

    @Test
    void testUpdateVisitPatientMedication_Success() {
        VisitPatientMedication med = new VisitPatientMedication();
        med.setMedicationId(3L);
        med.setMedicationName("OldName");
        med.setVisit(new PatientVisit());
        med.setPrescribedBy(new User());
        VisitPatientMedicationRequestDTO req = new VisitPatientMedicationRequestDTO();
        req.setMedicationName("NewName");
        req.setDosage("250mg");
        req.setFrequency("Once");
        req.setRoute("Oral");
        req.setStartDate(Date.valueOf("2023-03-01"));
        req.setEndDate(Date.valueOf("2023-03-10"));
        when(medicationRepository.findById(3L)).thenReturn(Optional.of(med));
        when(medicationRepository.save(any(VisitPatientMedication.class))).thenAnswer(i -> i.getArgument(0));
        VisitPatientMedicationResponseDTO dto = service.updateVisitPatientMedication(3L, req);
        assertEquals("NewName", dto.getMedicationName());
        assertEquals("250mg", dto.getDosage());
    }

    @Test
    void testUpdateVisitPatientMedication_NotFound() {
        VisitPatientMedicationRequestDTO req = new VisitPatientMedicationRequestDTO();
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MedicationNotFoundException.class, () -> service.updateVisitPatientMedication(99L, req));
    }

    @Test
    void testDeleteVisitPatientMedication_Success() {
        when(medicationRepository.existsById(4L)).thenReturn(true);
        doNothing().when(medicationRepository).deleteById(4L);
        assertDoesNotThrow(() -> service.deleteVisitPatientMedication(4L));
    }

    @Test
    void testDeleteVisitPatientMedication_NotFound() {
        when(medicationRepository.existsById(99L)).thenReturn(false);
        assertThrows(MedicationNotFoundException.class, () -> service.deleteVisitPatientMedication(99L));
    }
}

