package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * DTO for document response data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentManagementResponse {
    private Long documentId;
    private Long patientId;
    private Long visitId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long uploadedById;
    private Timestamp uploadedAt;
    private String description;

}

