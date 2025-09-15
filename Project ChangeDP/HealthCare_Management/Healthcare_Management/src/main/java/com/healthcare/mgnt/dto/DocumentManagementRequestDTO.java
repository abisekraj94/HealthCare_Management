package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * DTO for document creation/update requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentManagementRequestDTO {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long visitId;

    @NotBlank(message = "File name is required")
    private String fileName;

    private String fileType;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    @NotNull(message = "Uploaded by user ID is required")
    private Long uploadedById;

    private Timestamp uploadedAt;
    private String description;

    // Getters and setters
    // ...
}
