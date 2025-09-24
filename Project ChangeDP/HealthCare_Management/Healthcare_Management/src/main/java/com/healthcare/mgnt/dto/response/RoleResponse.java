package com.healthcare.mgnt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for role response data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long roleId;
    private String name;
    private String description;
    private Set<Long> permissionIds;

}

