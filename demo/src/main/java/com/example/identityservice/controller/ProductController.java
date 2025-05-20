package com.example.identityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.response.GettingAllProductResponse;
import com.example.identityservice.entity.Product;
import com.example.identityservice.service.ProductService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductController {
    ProductService productService;

    //lấy tất cả sản phẩm
    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {
        return ApiResponse.<List<Product>>builder()
            .result(productService.getAllProducts())
            .build();
    }
    
    //lấy tất cả sản phẩm trong tầm giá
    @GetMapping("/{minPrice}/{maxPrice}")
    public ApiResponse<List<Product>> getProductsByPrice(@PathVariable double minPrice, @PathVariable double maxPrice) {
        
        return ApiResponse.<List<Product>>builder()
            .result(productService.getAllProductsByPrice(minPrice, maxPrice))
            .build();
    }
    
    //lấy tất cả sản phẩm theo thể loại
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<Product>> getProductsByCategory(@PathVariable String categoryId) {
        
        return ApiResponse.<List<Product>>builder()
            .result(productService.getAllProductsByCategory(categoryId))
            .build();
    }
    
}
