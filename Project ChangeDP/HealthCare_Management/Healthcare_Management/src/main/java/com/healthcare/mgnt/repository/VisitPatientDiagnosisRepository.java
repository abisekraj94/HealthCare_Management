package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.VisitPatientDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitPatientDiagnosisRepository extends JpaRepository<VisitPatientDiagnosis, Long> {
}

