package com.healthcare.mgnt.repository;

import com.healthcare.mgnt.entity.UserPatientAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPatientAssignmentRepository extends JpaRepository<UserPatientAssignment, Long> {
}

