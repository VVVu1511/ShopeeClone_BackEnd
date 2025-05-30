package com.example.identityservice.dto.response;

import java.util.List;

import com.example.identityservice.entity.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GettingProductResponse {
    boolean success;

    List<Product> products;
}
