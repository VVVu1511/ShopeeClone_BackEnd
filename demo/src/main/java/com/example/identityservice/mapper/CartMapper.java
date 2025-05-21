package com.example.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.identityservice.dto.request.CartUpdateRequest;
import com.example.identityservice.entity.Cart;

@Mapper(
    componentModel = "spring"
)
public interface CartMapper {
    void updateCart(@MappingTarget Cart cart, CartUpdateRequest request);
} 
