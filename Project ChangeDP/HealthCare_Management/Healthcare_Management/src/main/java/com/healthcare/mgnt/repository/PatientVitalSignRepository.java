package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.PatientVitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVitalSignRepository extends JpaRepository<PatientVitalSign, Long> {
}

