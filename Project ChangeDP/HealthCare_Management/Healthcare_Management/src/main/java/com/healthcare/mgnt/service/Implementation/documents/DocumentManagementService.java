package com.healthcare.mgnt.service.Implementation.documents;

import com.healthcare.mgnt.dto.request.DocumentManagementRequest;
import com.healthcare.mgnt.dto.response.DocumentManagementResponse;
import com.healthcare.mgnt.entity.documents.DocumentManagement;
import com.healthcare.mgnt.repository.documents.DocumentManagementRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for document management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class DocumentManagementService implements IDocumentManagementService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentManagementService.class);
    @Autowired
    private DocumentManagementRepository documentManagementRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts DocumentManagementRequestDTO to DocumentManagement entity using ModelMapper.
     * @param dto DocumentManagementRequestDTO
     * @return DocumentManagement entity
     */
    private DocumentManagement toEntity(DocumentManagementRequest dto) {
        return modelMapper.map(dto, DocumentManagement.class);
    }

    /**
     * Converts DocumentManagement entity to DocumentManagementResponseDTO using ModelMapper.
     * @param entity DocumentManagement entity
     * @return DocumentManagementResponseDTO
     */
    private DocumentManagementResponse toResponseDTO(DocumentManagement entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, DocumentManagementResponse.class);
    }

    /**
     * Returns a paginated list of documents.
     * @param pageable Pageable object
     * @return Page of DocumentManagementResponseDTO
     */
    public Page<DocumentManagementResponse> getAllDocuments(Pageable pageable) {
        logger.info("Fetching all documents with pageable: {}", pageable);
        try {
            Page<DocumentManagement> entities = documentManagementRepository.findAll(pageable);
            return entities.map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching documents: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Returns a document by ID.
     * @param id Document ID
     * @return DocumentManagementResponseDTO or null if not found
     */
    public DocumentManagementResponse getDocumentById(Long id) {
        logger.info("Fetching document by id: {}", id);
        try {
            return documentManagementRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching document by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Creates a new document.
     * @param dto DocumentManagementRequestDTO
     * @return Created DocumentManagementResponseDTO
     */
    @Transactional
    public DocumentManagementResponse createDocument(DocumentManagementRequest dto) {
        logger.info("Creating document: {}", dto);
        try {
            DocumentManagement entity = toEntity(dto);
            DocumentManagement saved = documentManagementRepository.save(entity);
            logger.info("Document created with id: {}", saved.getDocumentId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating document: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Updates an existing document.
     * @param id Document ID
     * @param dto DocumentManagementRequestDTO
     * @return Updated DocumentManagementResponseDTO
     */
    @Transactional
    public DocumentManagementResponse updateDocument(Long id, DocumentManagementRequest dto) {
        logger.info("Updating document with id {}: {}", id, dto);
        try {
            DocumentManagement entity = documentManagementRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.DOCUMENT_NOT_FOUND));
            modelMapper.map(dto, entity);
            DocumentManagement updated = documentManagementRepository.save(entity);
            logger.info("Document updated with id: {}", updated.getDocumentId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Document not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating document id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }

    /**
     * Deletes a document by ID.
     * @param id Document ID
     */
    @Transactional
    public void deleteDocument(Long id) {
        logger.info("Deleting document with id: {}", id);
        try {
            if (!documentManagementRepository.existsById(id)) {
                logger.warn("Document not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.DOCUMENT_NOT_FOUND);
            }
            documentManagementRepository.deleteById(id);
            logger.info("Document deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting document id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.GENERIC_ERROR_CODE);
        }
    }
}
