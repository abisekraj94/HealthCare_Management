package com.healthcare.mgnt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * DTO for user-patient assignment creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatientAssignmentRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotBlank(message = "Assignment type is required")
    private String assignmentType;

    private Timestamp assignedAt;
    private Timestamp unassignedAt;

}
