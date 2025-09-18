package com.healthcare.mgnt.controller;

import com.healthcare.mgnt.dto.DocumentManagementRequest;
import com.healthcare.mgnt.dto.DocumentManagementResponse;
import com.healthcare.mgnt.service.Implementation.DocumentManagementService;
import com.healthcare.mgnt.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for document management operations.
 * Handles CRUD operations with DTOs, validation, logging, and structured error handling.
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentManagementController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentManagementController.class);

    @Autowired
    private DocumentManagementService documentManagementService;

    /**
     * Get all documents (paginated).
     */
    @Operation(summary = "Get all documents", description = "Returns a paginated list of documents")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "401", description = ApplicationConstants.UNAUTHORIZED)
    })
    @GetMapping
    public ResponseEntity<Page<DocumentManagementResponse>> getAllDocuments(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all documents, page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<DocumentManagementResponse> documents = documentManagementService.getAllDocuments(pageable);
            return ResponseEntity.ok(documents);
        } catch (Exception ex) {
            logger.error("Error fetching documents: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Get document by ID.
     */
    @Operation(summary = "Get document by ID", description = "Returns a document by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.SUCCESSFUL_OPERATION),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DOCUMENT_NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocumentManagementResponse> getDocumentById(@PathVariable Long id) {
        logger.info("Fetching document by id: {}", id);
        try {
            DocumentManagementResponse document = documentManagementService.getDocumentById(id);
            if (document == null) {
                logger.warn("Document not found for id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(document);
        } catch (Exception ex) {
            logger.error("Error fetching document by id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Create a new document.
     */
    @Operation(summary = "Create a new document", description = "Creates a new document")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = ApplicationConstants.CREATED),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PostMapping
    public ResponseEntity<DocumentManagementResponse> createDocument(@Validated @RequestBody DocumentManagementRequest requestDTO) {
        logger.info("Creating new document: {}", requestDTO.getFileName());
        try {
            DocumentManagementResponse createdDocument = documentManagementService.createDocument(requestDTO);
            return ResponseEntity.status(201).body(createdDocument);
        } catch (Exception ex) {
            logger.error("Error creating document: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Update an existing document.
     */
    @Operation(summary = "Update document", description = "Updates an existing document")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = ApplicationConstants.UPDATED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DOCUMENT_NOT_FOUND),
        @ApiResponse(responseCode = "400", description = ApplicationConstants.VALIDATION_ERROR)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DocumentManagementResponse> updateDocument(@PathVariable Long id, @Validated @RequestBody DocumentManagementRequest requestDTO) {
        logger.info("Updating document id: {}", id);
        try {
            DocumentManagementResponse updatedDocument = documentManagementService.updateDocument(id, requestDTO);
            return ResponseEntity.ok(updatedDocument);
        } catch (Exception ex) {
            logger.error("Error updating document id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete a document by ID.
     */
    @Operation(summary = "Delete document", description = "Deletes a document by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = ApplicationConstants.DELETED),
        @ApiResponse(responseCode = "404", description = ApplicationConstants.DOCUMENT_NOT_FOUND)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        logger.info("Deleting document id: {}", id);
        try {
            documentManagementService.deleteDocument(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting document id {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(500).build();
        }
    }
}
