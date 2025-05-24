package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.Cart;
import com.example.identityservice.entity.User;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByUser(User user);
}
