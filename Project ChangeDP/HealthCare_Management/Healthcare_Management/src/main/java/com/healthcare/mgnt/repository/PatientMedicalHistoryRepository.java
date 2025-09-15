package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.PatientMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientMedicalHistoryRepository extends JpaRepository<PatientMedicalHistory, Long> {
}

