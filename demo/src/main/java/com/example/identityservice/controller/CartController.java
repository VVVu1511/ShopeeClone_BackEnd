package com.example.identityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.CartCreationRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.entity.Cart;
import com.example.identityservice.repository.CartRepository;
import com.example.identityservice.service.CartService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/cart")

public class CartController {
    CartService cartService;

    @PostMapping
    public ApiResponse<Cart> addCart(@RequestBody CartCreationRequest request) {
        
        return ApiResponse.<Cart>builder()
            .result(cartService.addNewCart(request))
            .build();
    }
    
    
}
