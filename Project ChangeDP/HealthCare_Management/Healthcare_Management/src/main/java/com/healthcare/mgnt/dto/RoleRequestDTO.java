package com.healthcare.mgnt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * DTO for role creation/update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDTO {
    @NotBlank(message = "Role name is required")
    private String name;
    private String description;
    private Set<Long> permissionIds;
    // Getters and setters
    // ...
}
