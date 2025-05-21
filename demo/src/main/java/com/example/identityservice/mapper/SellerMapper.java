package com.example.identityservice.mapper;

import org.mapstruct.Mapper;

import com.example.identityservice.dto.request.SellerCreationRequest;
import com.example.identityservice.entity.Seller;

@Mapper(
    componentModel = "spring"
)
public interface SellerMapper {
    Seller toSeller(SellerCreationRequest request);
}
