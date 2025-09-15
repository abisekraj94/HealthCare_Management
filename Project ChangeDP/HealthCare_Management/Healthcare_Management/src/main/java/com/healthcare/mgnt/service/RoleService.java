package com.healthcare.mgnt.service;

import com.healthcare.mgnt.dto.RoleRequestDTO;
import com.healthcare.mgnt.dto.RoleResponseDTO;
import com.healthcare.mgnt.entity.Role;
import com.healthcare.mgnt.repository.RoleRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    private Role toEntity(RoleRequestDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }

    private RoleResponseDTO toResponseDTO(Role entity) {
        if (entity == null) return null;
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setRoleId(entity.getRoleId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public Page<RoleResponseDTO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public RoleResponseDTO getRoleById(Long id) {
        return roleRepository.findById(id).map(this::toResponseDTO).orElse(null);
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        Role role = toEntity(dto);
        Role saved = roleRepository.save(role);
        return toResponseDTO(saved);
    }

    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO dto) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new AppException(AppErrorCodes.ROLE_NOT_FOUND));
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        Role updated = roleRepository.save(role);
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
