package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for role permission creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionRequest {
    @NotBlank(message = "Permission name is required")
    private String name;
    private String description;

}
