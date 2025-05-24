package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.Review;
import com.example.identityservice.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    void deleteByUser(User user);
}
