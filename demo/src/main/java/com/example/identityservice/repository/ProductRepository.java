package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.Product;;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    
}

//correctness -> readability -> optimization