package com.example.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.GetPermissionsByRoleRequest;
import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.request.UpdateRoleRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.RoleResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.entity.Role;
import com.example.identityservice.service.PermissionService;
import com.example.identityservice.service.RoleService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class RoleController {
	RoleService roleService;
	
	//tạo 1 role mới
	@PostMapping
	public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
		return ApiResponse.<RoleResponse>builder()
				.result(roleService.create(request))
				.build();
	}
	
	//lấy mọi role
	@GetMapping
	public ApiResponse<List<RoleResponse>> getAll(){
		return ApiResponse.<List<RoleResponse>>builder()
				.result(roleService.getAll())
				.build();
	}
	
	//xóa role
	@DeleteMapping("/{role}")
	public ApiResponse<Void> delete(@PathVariable Long role){
		roleService.delete(role);
		return ApiResponse.<Void>builder().build();
	}
	
	//cập nhật role
	@PutMapping
	public ApiResponse<Role> updateRole(@RequestBody UpdateRoleRequest request) {
		
		return ApiResponse.<Role>builder()
			.result(roleService.updateRole(request))
			.build();
	}

	//lấy tất cả permissions của role đó
	@GetMapping("/permissions")
	public ApiResponse<List<Permission>> getPermissionsByRole(@RequestBody GetPermissionsByRoleRequest request) {
		
		return ApiResponse.<List<Permission>>builder()
			.result(roleService.getPermissionsByRole(request))
			.build();
	}
	
}
