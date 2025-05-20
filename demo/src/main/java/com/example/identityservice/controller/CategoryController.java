package com.example.identityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.CreatingCategoryRequest;
import com.example.identityservice.entity.Category;
import com.example.identityservice.repository.CategoryRepository;
import com.example.identityservice.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CategoryController {
    CategoryService categoryService;
    
    //thêm thể loại
    @PostMapping("/add")
    public ApiResponse<Category> addCategory(@RequestBody CreatingCategoryRequest request) {
        
        return ApiResponse.<Category>builder()
            .result(categoryService.newCategory(request))
            .build();
    }
    

}


