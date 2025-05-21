package com.example.identityservice.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.DeletingProductRequest;
import com.example.identityservice.dto.request.GettingProductRequest;
import com.example.identityservice.dto.request.SellerCreationRequest;
import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.dto.request.UploadingProductRequest;
import com.example.identityservice.dto.response.DeletingProductResponse;
import com.example.identityservice.dto.response.GettingProductResponse;
import com.example.identityservice.dto.response.UpdatingProductResponse;
import com.example.identityservice.dto.response.UploadingProductResponse;
import com.example.identityservice.entity.Category;
import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.Seller;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.ProductMapper;
import com.example.identityservice.mapper.SellerMapper;
import com.example.identityservice.repository.CategoryRepository;
import com.example.identityservice.repository.ProductRepository;
import com.example.identityservice.repository.SellerRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SellerService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;
    SellerRepository sellerRepository;
    SellerMapper sellerMapper;

    //dang ban san pham
    public UploadingProductResponse uploadProduct(UploadingProductRequest request){
        
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST_CATEGORY));
    
        productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(category)
                .description(request.getDescription())
                .stockQty(request.getStockQty())
                .createdAt(LocalDateTime.now())
                .seller(sellerRepository.findById(request.getSellerId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST)))
                .build());

        return UploadingProductResponse.builder().success(true).build();
    }

    //cap nhat san pham
    public UpdatingProductResponse updateProduct(UpdatingProductRequest request){
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));

        try{
            productMapper.updateProduct(product, request);
            productRepository.save(product);
        }
        catch(Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        
        return UpdatingProductResponse.builder().success(false).build();
    }

    //lay danh sach san pham
    public List<Product> getProducts(GettingProductRequest request){
        Seller seller = sellerRepository.findById
            (request.getSellerId())
            .orElseThrow(
                () -> new AppException(ErrorCode.NOT_EXIST)
            );
        
        return productRepository.findAllBySeller(seller);
    }

    //xoa san pham
    public DeletingProductResponse deleteProduct(DeletingProductRequest request){
        try{
            productRepository.deleteById(request.getProductId());
        } catch(Exception e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return DeletingProductResponse.builder().success(true).build();
    }

    //tao mot seller moi
    public Seller createSeller(SellerCreationRequest request){
        return sellerRepository.save(Seller.builder()
                                .shopName(request.getShopName())
                                .shopDescription(request.getShopDescription())
                                .createdAt(LocalDateTime.now())
                                .build()
    );
    }

    //lay tat ca seller
    public List<Seller> getAllSellers(){
        return sellerRepository.findAll();
    }

    public void deleteSeller(Long sellerId){
        boolean done = true;
        
        try{
            sellerRepository.deleteById(sellerId);
        } catch(Exception e){
            done = false;
        }
    }
}
