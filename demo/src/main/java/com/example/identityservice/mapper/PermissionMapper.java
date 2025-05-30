package com.example.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.UpdatePermissionRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.entity.User;

@Mapper(
	componentModel = "spring"
)

public interface PermissionMapper {
	Permission toPermission(PermissionRequest request);
	PermissionResponse toPermissionResponse(Permission permission);
	Permission toPermission(UpdatePermissionRequest request);

	void updatePermission(@MappingTarget Permission permission, UpdatePermissionRequest request);
}
