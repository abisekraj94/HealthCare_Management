package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for role permission response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponseDTO {
    private Long permissionId;
    private String name;
    private String description;
    // Getters and setters
    // ...
}

