package com.example.identityservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.Permission;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	Permission findByName(String name);
    List<Permission> findAllByNameIn(Set<String> permissions);
    boolean existsByName(String name);
}
