package com.example.identityservice.dto.request;

import java.time.LocalDateTime;

import com.example.identityservice.entity.Product;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewProductRequest {
    String userId;
    String productId;

    Integer rating;
    String comment;
    LocalDateTime createdAt;
}
