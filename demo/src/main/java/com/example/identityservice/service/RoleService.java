package com.example.identityservice.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.response.RoleResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.RolePermission;
import com.example.identityservice.entity.RolePermissionId;
import com.example.identityservice.mapper.PermissionMapper;
import com.example.identityservice.mapper.RoleMapper;
import com.example.identityservice.repository.PermissionRepository;
import com.example.identityservice.repository.RolePermissionRepository;
import com.example.identityservice.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
	RoleRepository roleRepository;
	PermissionRepository permissionRepository;
	RolePermissionRepository rolePermissionRepository;
	RoleMapper roleMapper;
	
	public RoleResponse create(RoleRequest request) {
		Role role = roleMapper.toRole(request);
		
		//find by permission name
		List<Permission> permissions = permissionRepository.findAllByNameIn(request.getPermissions());
	
		final Role save_role = roleRepository.save(role);

		List<RolePermission> rpl = permissions.stream()
			.map(p -> RolePermission.builder()
				.rolePermissionId(RolePermissionId.builder().roleId(save_role.getRoleId()).permissionId(p.getPermissionId()).build())
				.role(save_role)
				.permission(p)
				.build())
			.toList();

		rolePermissionRepository.saveAll(rpl);
		
		return roleMapper.toRoleResponse(role);
	}
	
	public List<RoleResponse> getAll(){
		return roleRepository.findAll()
				.stream()
				.map(roleMapper::toRoleResponse)
				.toList();
	}
	
	public void delete(Long roleId) {
		roleRepository.deleteById(roleId);
	}
}
