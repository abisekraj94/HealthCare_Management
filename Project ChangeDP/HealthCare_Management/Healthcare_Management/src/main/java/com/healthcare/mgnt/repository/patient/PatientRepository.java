package com.healthcare.mgnt.repository.patient;

/**
 * Repository interface for Patient entity CRUD operations.
 */
import com.healthcare.mgnt.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("update Patient p set p.isActive = true where p.patientId = ?1")
    void deleteByPatientId(Long id);
}
