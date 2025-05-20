package com.example.identityservice.dto.response;

import java.util.List;

import com.example.identityservice.entity.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class GettingAllProductResponse {
    boolean success;
    List<Product> products;
}
