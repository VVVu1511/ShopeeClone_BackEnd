package com.example.identityservice.dto.request;

import com.example.identityservice.entity.Cart;
import com.example.identityservice.entity.Product;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class BuyingProductRequest {
        String cartId;
        String productId;
        Integer quantity;
}
