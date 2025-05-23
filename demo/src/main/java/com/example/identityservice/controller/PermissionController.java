package com.example.identityservice.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.UpdatePermissionRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.service.PermissionService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class PermissionController {
	PermissionService permissionService;
	
	//tạo 1 permission mới
	@PostMapping
	public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
		return ApiResponse.<PermissionResponse>builder()
				.result(permissionService.create(request))
				.build();
	}
	
	//lấy tất cả permissions
	@GetMapping
	public ApiResponse<List<PermissionResponse>> getAll(){
		return ApiResponse.<List<PermissionResponse>>builder()
				.result(permissionService.getAll())
				.build();
	}
	
	//xóa 1 permission
	@DeleteMapping("/{permissionId}")
	public ApiResponse<Void> delete(@PathVariable Long permissionId){
		permissionService.delete(permissionId);
		return ApiResponse.<Void>builder().build();
	}
	
	//cập nhật 1 permission
	@PutMapping
	public ApiResponse<Permission> updatePermission(@RequestBody UpdatePermissionRequest request) {
		
		return ApiResponse.<Permission>builder()
			.result(permissionService.updatePermission(request))
			.build();
	}

}
