package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.DocumentManagementRequestDTO;
import com.healthcare.mgnt.dto.DocumentManagementResponseDTO;
import com.healthcare.mgnt.entity.DocumentManagement;
import com.healthcare.mgnt.repository.DocumentManagementRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class DocumentManagementService {
    @Autowired
    private DocumentManagementRepository documentManagementRepository;

    private DocumentManagementResponseDTO toResponseDTO(DocumentManagement entity) {
        if (entity == null) return null;
        DocumentManagementResponseDTO dto = new DocumentManagementResponseDTO();
        dto.setDocumentId(entity.getDocumentId());
        dto.setPatientId(entity.getPatient() != null ? entity.getPatient().getPatientId() : null);
        dto.setVisitId(entity.getVisit() != null ? entity.getVisit().getVisitId() : null);
        dto.setFileName(entity.getFileName());
        dto.setFileType(entity.getFileType());
        dto.setFileUrl(entity.getFileUrl());
        dto.setUploadedById(entity.getUploadedBy() != null ? entity.getUploadedBy().getUserId() : null);
        dto.setUploadedAt(entity.getUploadedAt());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    private DocumentManagement toEntity(DocumentManagementRequestDTO dto) {
        DocumentManagement entity = new DocumentManagement();
        // Set patient, visit, uploadedBy using repository/service if needed
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setFileUrl(dto.getFileUrl());
        entity.setUploadedAt(dto.getUploadedAt());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public Page<DocumentManagementResponseDTO> getAllDocuments(Pageable pageable) {
        Page<DocumentManagement> entities = documentManagementRepository.findAll(pageable);
        return new PageImpl<>(entities.getContent().stream().map(this::toResponseDTO).collect(Collectors.toList()), pageable, entities.getTotalElements());
    }

    public DocumentManagementResponseDTO getDocumentById(Long id) {
        DocumentManagement entity = documentManagementRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.DOCUMENT_NOT_FOUND));
        return toResponseDTO(entity);
    }

    @Transactional
    public DocumentManagementResponseDTO createDocument(DocumentManagementRequestDTO dto) {
        DocumentManagement entity = toEntity(dto);
        DocumentManagement saved = documentManagementRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional
    public DocumentManagementResponseDTO updateDocument(Long id, DocumentManagementRequestDTO dto) {
        DocumentManagement document = documentManagementRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.DOCUMENT_NOT_FOUND));
        document.setFileName(dto.getFileName());
        document.setFileType(dto.getFileType());
        document.setFileUrl(dto.getFileUrl());
        document.setUploadedAt(dto.getUploadedAt());
        document.setDescription(dto.getDescription());
        DocumentManagement updated = documentManagementRepository.save(document);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteDocument(Long id) {
        documentManagementRepository.deleteById(id);
    }
}
