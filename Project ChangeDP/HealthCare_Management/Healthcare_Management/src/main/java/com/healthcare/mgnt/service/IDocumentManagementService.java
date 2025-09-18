package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.DocumentManagementRequest;
import com.healthcare.mgnt.dto.DocumentManagementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing healthcare documents such as reports, prescriptions, and other patient-related files.
 * Provides CRUD operations and paginated retrieval of documents.
 */
public interface IDocumentManagementService {
    /**
     * Retrieves a paginated list of all documents in the system.
     * @param pageable pagination information
     * @return paginated list of document response DTOs
     */
    Page<DocumentManagementResponse> getAllDocuments(Pageable pageable);

    /**
     * Retrieves a document by its unique identifier.
     * @param id document ID
     * @return document response DTO
     */
    DocumentManagementResponse getDocumentById(Long id);

    /**
     * Creates a new document record in the system.
     * @param dto document request DTO containing document details
     * @return created document response DTO
     */
    DocumentManagementResponse createDocument(DocumentManagementRequest dto);

    /**
     * Updates an existing document record.
     * @param id document ID
     * @param dto document request DTO with updated details
     * @return updated document response DTO
     */
    DocumentManagementResponse updateDocument(Long id, DocumentManagementRequest dto);

    /**
     * Deletes a document by its unique identifier.
     * @param id document ID
     */
    void deleteDocument(Long id);
}
