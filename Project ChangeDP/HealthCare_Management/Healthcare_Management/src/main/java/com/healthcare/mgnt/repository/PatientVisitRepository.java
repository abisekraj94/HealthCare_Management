package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVisitRepository extends JpaRepository<PatientVisit, Long> {
}

