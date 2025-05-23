package com.example.identityservice.repository;

import java.security.Permission;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.RolePermission;
import com.example.identityservice.entity.RolePermissionId;

public interface RolePermissionRepository extends JpaRepository<RolePermission,RolePermissionId>{
    
}
