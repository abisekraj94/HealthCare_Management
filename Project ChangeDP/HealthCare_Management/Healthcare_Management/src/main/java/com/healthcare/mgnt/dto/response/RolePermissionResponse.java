package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for role permission response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionResponse {
    private Long permissionId;
    private String name;
    private String description;

}

