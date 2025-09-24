package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * DTO for patient response data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientResponse {
    private Long patientId;
    private String mrn;
    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private Long primaryPhysicianId;
    private Long referralPhysicianId;
}
