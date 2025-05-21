package com.example.identityservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.CartCreationRequest;
import com.example.identityservice.entity.Cart;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.CartRepository;
import com.example.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CartService {
    CartRepository cartRepository;
    UserRepository userRepository;

    public Cart addNewCart(CartCreationRequest request){
        return cartRepository.save(Cart.builder()
                .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST)))
                .createdAt(LocalDateTime.now())
                .build());
    }
}
