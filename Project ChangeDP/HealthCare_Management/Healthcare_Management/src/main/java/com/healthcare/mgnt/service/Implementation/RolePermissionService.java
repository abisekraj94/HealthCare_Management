package com.healthcare.mgnt.service.Implementation;

import com.healthcare.mgnt.dto.RolePermissionRequest;
import com.healthcare.mgnt.dto.RolePermissionResponse;
import com.healthcare.mgnt.entity.RolePermission;
import com.healthcare.mgnt.repository.RolePermissionRepository;
import com.healthcare.mgnt.exception.AppException;
import com.healthcare.mgnt.constants.AppErrorCodes;
import com.healthcare.mgnt.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for role permission management.
 * Handles CRUD operations, DTO conversion, and error handling.
 */
@Service
public class RolePermissionService implements IRolePermissionService {
    private static final Logger logger = LoggerFactory.getLogger(RolePermissionService.class);
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts RolePermissionRequestDTO to RolePermission entity using ModelMapper.
     * @param dto RolePermissionRequestDTO
     * @return RolePermission entity
     */
    private RolePermission toEntity(RolePermissionRequest dto) {
        return modelMapper.map(dto, RolePermission.class);
    }

    /**
     * Converts RolePermission entity to RolePermissionResponseDTO using ModelMapper.
     * @param entity RolePermission entity
     * @return RolePermissionResponseDTO
     */
    private RolePermissionResponse toResponseDTO(RolePermission entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, RolePermissionResponse.class);
    }

    /**
     * Returns a paginated list of role permissions.
     * @param pageable Pageable object
     * @return Page of RolePermissionResponseDTO
     */
    public Page<RolePermissionResponse> getAllRolePermissions(Pageable pageable) {
        logger.info("Fetching all role permissions with pageable: {}", pageable);
        try {
            return rolePermissionRepository.findAll(pageable).map(this::toResponseDTO);
        } catch (Exception ex) {
            logger.error("Error fetching role permissions: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Returns a role permission by ID.
     * @param id Permission ID
     * @return RolePermissionResponseDTO or null if not found
     */
    public RolePermissionResponse getRolePermissionById(Long id) {
        logger.info("Fetching role permission by id: {}", id);
        try {
            return rolePermissionRepository.findById(id).map(this::toResponseDTO).orElse(null);
        } catch (Exception ex) {
            logger.error("Error fetching role permission by id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Creates a new role permission.
     * @param dto RolePermissionRequestDTO
     * @return Created RolePermissionResponseDTO
     */
    @Transactional
    public RolePermissionResponse createRolePermission(RolePermissionRequest dto) {
        logger.info("Creating role permission: {}", dto);
        try {
            RolePermission permission = toEntity(dto);
            RolePermission saved = rolePermissionRepository.save(permission);
            logger.info("Role permission created with id: {}", saved.getPermissionId());
            return toResponseDTO(saved);
        } catch (Exception ex) {
            logger.error("Error creating role permission: {}", ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Updates an existing role permission.
     * @param id Permission ID
     * @param dto RolePermissionRequestDTO
     * @return Updated RolePermissionResponseDTO
     */
    @Transactional
    public RolePermissionResponse updateRolePermission(Long id, RolePermissionRequest dto) {
        logger.info("Updating role permission with id {}: {}", id, dto);
        try {
            RolePermission permission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new AppException(AppErrorCodes.PERMISSION_NOT_FOUND, "Failed to update patient medical history"));
            modelMapper.map(dto, permission);
            RolePermission updated = rolePermissionRepository.save(permission);
            logger.info("Role permission updated with id: {}", updated.getPermissionId());
            return toResponseDTO(updated);
        } catch (AppException ex) {
            logger.warn("Role permission not found for update, id: {}", id);
            throw ex;
        } catch (Exception ex) {
            logger.error("Error updating role permission id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }

    /**
     * Deletes a role permission by ID.
     * @param id Permission ID
     */
    @Transactional
    public void deleteRolePermission(Long id) {
        logger.info("Deleting role permission with id: {}", id);
        try {
            if (!rolePermissionRepository.existsById(id)) {
                logger.warn("Role permission not found for delete, id: {}", id);
                throw new AppException(AppErrorCodes.PERMISSION_NOT_FOUND, "Failed to update patient medical history");
            }
            rolePermissionRepository.deleteById(id);
            logger.info("Role permission deleted with id: {}", id);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Error deleting role permission id {}: {}", id, ex.getMessage(), ex);
            throw new AppException(AppErrorCodes.INTERNAL_ERROR, "Failed to update patient medical history");
        }
    }
}
