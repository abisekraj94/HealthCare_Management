package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.VisitPatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitPatientMedicationRepository extends JpaRepository<VisitPatientMedication, Long> {
}

