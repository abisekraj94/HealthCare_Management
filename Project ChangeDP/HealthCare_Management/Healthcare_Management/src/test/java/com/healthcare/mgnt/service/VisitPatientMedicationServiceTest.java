package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.request.VisitPatientMedicationRequest;
import com.healthcare.mgnt.dto.response.VisitPatientMedicationResponse;
import com.healthcare.mgnt.entity.patient.VisitPatientMedication;
import com.healthcare.mgnt.entity.patient.PatientVisit;
import com.healthcare.mgnt.entity.user.User;
import com.healthcare.mgnt.repository.patient.VisitPatientMedicationRepository;
import com.healthcare.mgnt.repository.patient.PatientVisitRepository;
import com.healthcare.mgnt.repository.user.UserRepository;
import com.healthcare.mgnt.exception.MedicationException;
import com.healthcare.mgnt.service.Implementation.patient.VisitPatientMedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;

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
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VisitPatientMedicationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Setup ModelMapper mock behavior for DTO/entity conversion
        when(modelMapper.map(any(VisitPatientMedicationRequest.class), eq(VisitPatientMedication.class)))
            .thenAnswer(invocation -> {
                VisitPatientMedicationRequest dto = invocation.getArgument(0);
                VisitPatientMedication entity = new VisitPatientMedication();
                entity.setMedicationName(dto.getMedicationName());
                entity.setDosage(dto.getDosage());
                entity.setFrequency(dto.getFrequency());
                entity.setRoute(dto.getRoute());
                entity.setStartDate(dto.getStartDate());
                entity.setEndDate(dto.getEndDate());
                PatientVisit visit = new PatientVisit();
                visit.setVisitId(dto.getVisitId());
                entity.setVisit(visit);
                User user = new User();
                user.setUserId(dto.getPrescribedById());
                entity.setPrescribedBy(user);
                return entity;
            });
        // Fix: Use doAnswer for overloaded map method
        doAnswer(invocation -> {
            VisitPatientMedicationRequest dto = invocation.getArgument(0);
            VisitPatientMedication entity = invocation.getArgument(1);
            entity.setMedicationName(dto.getMedicationName());
            entity.setDosage(dto.getDosage());
            entity.setFrequency(dto.getFrequency());
            entity.setRoute(dto.getRoute());
            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            // Optionally update visit and prescribedBy if needed
            return entity;
        }).when(modelMapper).map(any(VisitPatientMedicationRequest.class), any(VisitPatientMedication.class));
        when(modelMapper.map(any(VisitPatientMedication.class), eq(VisitPatientMedicationResponse.class)))
            .thenAnswer(invocation -> {
                VisitPatientMedication entity = invocation.getArgument(0);
                VisitPatientMedicationResponse dto = new VisitPatientMedicationResponse();
                dto.setMedicationId(entity.getMedicationId());
                dto.setMedicationName(entity.getMedicationName());
                dto.setDosage(entity.getDosage());
                dto.setFrequency(entity.getFrequency());
                dto.setRoute(entity.getRoute());
                dto.setStartDate(entity.getStartDate());
                dto.setEndDate(entity.getEndDate());
                dto.setVisitId(entity.getVisit() != null ? entity.getVisit().getVisitId() : null);
                dto.setPrescribedById(entity.getPrescribedBy() != null ? entity.getPrescribedBy().getUserId() : null);
                return dto;
            });
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
        Page<VisitPatientMedicationResponse> result = service.getAllVisitPatientMedications(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("Aspirin", result.getContent().get(0).getMedicationName());
    }

    @Test
    void testGetVisitPatientMedicationById_Success() {
        VisitPatientMedication med = new VisitPatientMedication();
        med.setMedicationId(2L);
        med.setMedicationName("Ibuprofen");
        when(medicationRepository.findById(2L)).thenReturn(Optional.of(med));
        VisitPatientMedicationResponse dto = service.getVisitPatientMedicationById(2L);
        assertEquals("Ibuprofen", dto.getMedicationName());
        assertEquals(2L, dto.getMedicationId());
    }

    @Test
    void testGetVisitPatientMedicationById_NotFound() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MedicationException.class, () -> service.getVisitPatientMedicationById(99L));
    }

    @Test
    void testCreateVisitPatientMedication_Success() {
        VisitPatientMedicationRequest req = new VisitPatientMedicationRequest();
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
        VisitPatientMedicationResponse dto = service.createVisitPatientMedication(req);
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
        VisitPatientMedicationRequest req = new VisitPatientMedicationRequest();
        req.setMedicationName("NewName");
        req.setDosage("250mg");
        req.setFrequency("Once");
        req.setRoute("Oral");
        req.setStartDate(Date.valueOf("2023-03-01"));
        req.setEndDate(Date.valueOf("2023-03-10"));
        when(medicationRepository.findById(3L)).thenReturn(Optional.of(med));
        when(medicationRepository.save(any(VisitPatientMedication.class))).thenAnswer(i -> i.getArgument(0));
        VisitPatientMedicationResponse dto = service.updateVisitPatientMedication(3L, req);
        assertEquals("NewName", dto.getMedicationName());
        assertEquals("250mg", dto.getDosage());
    }

    @Test
    void testUpdateVisitPatientMedication_NotFound() {
        VisitPatientMedicationRequest req = new VisitPatientMedicationRequest();
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MedicationException.class, () -> service.updateVisitPatientMedication(99L, req));
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
        assertThrows(MedicationException.class, () -> service.deleteVisitPatientMedication(99L));
    }
}
