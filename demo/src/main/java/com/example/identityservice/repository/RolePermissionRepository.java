package com.example.identityservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.Permission;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.RolePermission;
import com.example.identityservice.entity.RolePermissionId;

public interface RolePermissionRepository extends JpaRepository<RolePermission,RolePermissionId>{
    List<RolePermission> findAllByRole(Role role);
}
