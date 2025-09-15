package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.DocumentManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentManagementRepository extends JpaRepository<DocumentManagement, Long> {
}

