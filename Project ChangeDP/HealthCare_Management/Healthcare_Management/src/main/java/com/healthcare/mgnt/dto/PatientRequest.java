package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

/**
 * DTO for patient creation/update requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientRequest {
    @NotBlank(message = "MRN is required")
    private String mrn;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    private Date dob;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String phone;
    private String email;
    private String address;
    private Long primaryPhysicianId;
    private Long referralPhysicianId;

}
