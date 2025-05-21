package com.example.identityservice.dto.request;

import java.time.LocalDateTime;

import com.example.identityservice.entity.Seller;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data

public class UploadingProductRequest {
    Long sellerId;
    
    Long categoryId;
    String name;
    String description;
    Double price;
    Integer stockQty;
}
