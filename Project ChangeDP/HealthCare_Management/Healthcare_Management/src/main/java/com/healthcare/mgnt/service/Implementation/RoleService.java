package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.RoleRequest;
import com.healthcare.mgnt.dto.RoleResponse;
import com.healthcare.mgnt.entity.Role;
import com.healthcare.mgnt.repository.RoleRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for role management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class RoleService implements IRoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts RoleRequestDTO to Role entity using ModelMapper.
     * @param dto RoleRequestDTO
     * @return Role entity
     */
    private Role toEntity(RoleRequest dto) {
        return modelMapper.map(dto, Role.class);
    }

    /**
     * Converts Role entity to RoleResponseDTO using ModelMapper.
     * @param entity Role entity
     * @return RoleResponseDTO
     */
    private RoleResponse toResponseDTO(Role entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, RoleResponse.class);
    }

    /**
     * Returns a paginated list of roles.
     * @param pageable Pageable object
     * @return Page of RoleResponseDTO
     */
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        logger.info("Fetching all roles with pageable: {}", pageable);
        try {
            return roleRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching roles: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch roles");
        }
    }

    /**
     * Returns a role by ID.
     * @param id Role ID
     * @return RoleResponseDTO or null if not found
     */
    public RoleResponse getRoleById(Long id) {
        logger.info("Fetching role by id: {}", id);
        try {
            return roleRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching role by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to fetch role");
        }
    }

    /**
     * Creates a new role.
     * @param dto RoleRequestDTO
     * @return Created RoleResponseDTO
     */
    @Transactional
    public RoleResponse createRole(RoleRequest dto) {
        logger.info("Creating role: {}", dto);
        try {
            Role role = toEntity(dto);
            Role saved = roleRepository.save(role);
            logger.info("Role created with id: {}", saved.getRoleId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating role: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to create role");
        }
    }

    /**
     * Updates an existing role.
     * @param id Role ID
     * @param dto RoleRequestDTO
     * @return Updated RoleResponseDTO
     */
    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest dto) {
        logger.info("Updating role with id {}: {}", id, dto);
        try {
            Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.ROLE_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, role);
            Role updated = roleRepository.save(role);
            logger.info("Role updated with id: {}", updated.getRoleId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Role not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating role id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update role");
        }
    }

    /**
     * Deletes a role by ID.
     * @param id Role ID
     */
    @Transactional
    public void deleteRole(Long id) {
        logger.info("Deleting role with id: {}", id);
        try {
            if (!roleRepository.existsById(id)) {
                logger.warn("Role not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.ROLE_NOT_FOUND, "Failed to update patient medical history");
            }
            roleRepository.deleteById(id);
            logger.info("Role deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting role id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to delete role");
        }
    }
}
