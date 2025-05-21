package com.example.identityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.DeletingProductRequest;
import com.example.identityservice.dto.request.GettingProductRequest;
import com.example.identityservice.dto.request.SellerCreationRequest;
import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.dto.request.UploadingProductRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.DeletingProductResponse;
import com.example.identityservice.dto.response.GettingProductResponse;
import com.example.identityservice.dto.response.UpdatingProductResponse;
import com.example.identityservice.dto.response.UploadingProductResponse;
import com.example.identityservice.entity.Seller;
import com.example.identityservice.service.SellerService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerController {
    SellerService sellerService;
    
    //đăng bán sản phẩm
    @PostMapping("/upload")
    public ApiResponse<UploadingProductResponse> uploadProduct(@RequestBody UploadingProductRequest request) {
        
        return ApiResponse.<UploadingProductResponse>builder()
            .result(sellerService.uploadProduct(request))
            .build();
    }
    
    //cập nhật sản phẩm
    @PutMapping("/update")
    public ApiResponse<UpdatingProductResponse> updateProduct(@RequestBody UpdatingProductRequest request){
        
        return ApiResponse.<UpdatingProductResponse>builder()
            .result(sellerService.updateProduct(request))
            .build();
    }

    //lấy tất cả sản phẩm của sellerId
    @GetMapping("/products")
    public ApiResponse<GettingProductResponse> getProducts(@RequestBody GettingProductRequest request) {
        
        return ApiResponse.<GettingProductResponse>builder()
            .result(sellerService.getProducts(request))
            .build();
    }
    
    //xóa sản phẩm
    @DeleteMapping("/delete")
    public ApiResponse<DeletingProductResponse> deleteProduct(@RequestBody DeletingProductRequest request){

        return ApiResponse.<DeletingProductResponse>builder()
            .result(sellerService.deleteProduct(request))
            .build();
    }

    //tạo ra 1 seller mới
    @PostMapping("/create")
    public ApiResponse<Seller> createSeller(@RequestBody SellerCreationRequest request) {
        
        return ApiResponse.<Seller>builder()
            .result(sellerService.createSeller(request))
            .build();
    }

    //lấy tất cả sellers
    @GetMapping("/all")
    public ApiResponse<List<Seller>> getAllSellers(){

        return ApiResponse.<List<Seller>>builder()
            .result(sellerService.getAllSellers())
            .build();
    }
    
}