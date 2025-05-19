package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.identityservice.entity.CartItem;
import com.example.identityservice.entity.CartItemId;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    
}
