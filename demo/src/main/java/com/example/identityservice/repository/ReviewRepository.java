package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, String>{
    
}
