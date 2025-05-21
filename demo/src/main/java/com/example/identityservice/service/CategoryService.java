package com.example.identityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.identityservice.dto.request.CreatingCategoryRequest;
import com.example.identityservice.entity.Category;
import com.example.identityservice.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    
    public Category newCategory(CreatingCategoryRequest request){

        return Category.builder()
            .name(request.getName())
            .build();
    }
}
