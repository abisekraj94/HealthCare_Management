package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.PatientIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientIdentifierRepository extends JpaRepository<PatientIdentifier, Long> {
}

