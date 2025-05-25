package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.RoleUser;
import com.example.identityservice.entity.RoleUserId;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserId>{
    
    @Modifying
    @Query("DELETE from RoleUser ru where ru.id.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
