package com.healthcare.mgnt.controller.documents;
import com.healthcare.mgnt.dto.request.DocumentManagementRequest;
import com.healthcare.mgnt.dto.response.DocumentManagementResponse;
import com.healthcare.mgnt.service.Implementation.documents.DocumentManagementService;
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
    public Page<DocumentManagementResponse> getAllDocuments(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int size) {
        logger.info("Fetching all documents, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return documentManagementService.getAllDocuments(pageable);
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
    public DocumentManagementResponse getDocumentById(@PathVariable Long id) {
        logger.info("Fetching document by id: {}", id);
        DocumentManagementResponse document = documentManagementService.getDocumentById(id);
        if (document == null) {
            logger.error("Document not found for id: {}", id);
            throw new RuntimeException("Document not found"); // Replace with custom exception if available
        }
        return document;
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
    public DocumentManagementResponse createDocument(@Validated @RequestBody DocumentManagementRequest requestDTO) {
        logger.info("Creating new document: {}", requestDTO.getFileName());
        return documentManagementService.createDocument(requestDTO);
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
    public DocumentManagementResponse updateDocument(@PathVariable Long id, @Validated @RequestBody DocumentManagementRequest requestDTO) {
        logger.info("Updating document id: {}", id);
        return documentManagementService.updateDocument(id, requestDTO);
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
    public void deleteDocument(@PathVariable Long id) {
        logger.info("Deleting document id: {}", id);
        documentManagementService.deleteDocument(id);
    }
}
