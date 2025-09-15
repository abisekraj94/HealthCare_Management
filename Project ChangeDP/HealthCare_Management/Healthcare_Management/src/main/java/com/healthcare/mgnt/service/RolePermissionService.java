package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.RolePermissionRequestDTO;
import com.healthcare.mgnt.dto.RolePermissionResponseDTO;
import com.healthcare.mgnt.entity.RolePermission;
import com.healthcare.mgnt.repository.RolePermissionRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    private RolePermission toEntity(RolePermissionRequestDTO dto) {
        RolePermission permission = new RolePermission();
        permission.setName(dto.getName());
        permission.setDescription(dto.getDescription());
        return permission;
    }

    private RolePermissionResponseDTO toResponseDTO(RolePermission entity) {
        if (entity == null) return null;
        RolePermissionResponseDTO dto = new RolePermissionResponseDTO();
        dto.setPermissionId(entity.getPermissionId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public Page<RolePermissionResponseDTO> getAllRolePermissions(Pageable pageable) {
        return rolePermissionRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public RolePermissionResponseDTO getRolePermissionById(Long id) {
        return rolePermissionRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public RolePermissionResponseDTO createRolePermission(RolePermissionRequestDTO dto) {
        RolePermission permission = toEntity(dto);
        RolePermission saved = rolePermissionRepository.save(permission);
        return toResponseDTO(saved);
    }

    @Transactional
    public RolePermissionResponseDTO updateRolePermission(Long id, RolePermissionRequestDTO dto) {
        RolePermission permission = rolePermissionRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.PERMISSION_NOT_FOUND));
        permission.setName(dto.getName());
        permission.setDescription(dto.getDescription());
        RolePermission updated = rolePermissionRepository.save(permission);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteRolePermission(Long id) {
        rolePermissionRepository.deleteById(id);
    }
}
