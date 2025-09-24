package com.healthcare.mgnt.repository.documents;

import com.healthcare.mgnt.entity.documents.DocumentManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for DocumentManagement entity.
 * Provides CRUD operations and custom queries for document management in the HealthCare Management System.
 */
@Repository
public interface DocumentManagementRepository extends JpaRepository<DocumentManagement, Long> {
    // Inherits standard CRUD and query methods from JpaRepository.
}
