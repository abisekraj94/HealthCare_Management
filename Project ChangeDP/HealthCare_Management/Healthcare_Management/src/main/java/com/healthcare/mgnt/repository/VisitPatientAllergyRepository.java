package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.VisitPatientAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitPatientAllergyRepository extends JpaRepository<VisitPatientAllergy, Long> {
}

