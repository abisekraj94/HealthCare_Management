package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * DTO for user-patient assignment response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatientAssignmentResponseDTO {
    private Long assignmentId;
    private Long userId;
    private Long patientId;
    private String assignmentType;
    private Timestamp assignedAt;
    private Timestamp unassignedAt;
    // Getters and setters
    // ...
}

