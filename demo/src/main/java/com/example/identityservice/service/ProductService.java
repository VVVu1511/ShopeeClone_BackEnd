package com.example.identityservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identityservice.entity.Product;
import com.example.identityservice.repository.ProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByPrice(double minPrice, double maxPrice){
        return productRepository.findAll().stream().filter(e -> e.getPrice() >= minPrice && e.getPrice() <= maxPrice).toList();
    }

    public List<Product> getAllProductsByCategory(Long categoryId){
        return productRepository.findAll().stream().filter(e -> e.getCategory().getCategoryId() == categoryId).toList();
    }
}
