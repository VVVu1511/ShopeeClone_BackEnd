package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{
    
}
