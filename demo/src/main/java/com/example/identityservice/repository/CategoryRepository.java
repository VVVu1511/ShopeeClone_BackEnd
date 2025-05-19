package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{
    
}
