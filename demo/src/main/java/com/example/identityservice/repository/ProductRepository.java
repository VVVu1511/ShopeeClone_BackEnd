package com.example.identityservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.Seller;;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findAllBySeller(Seller seller);
}

//correctness -> readability -> optimization