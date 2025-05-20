package com.example.identityservice.dto.request;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.identityservice.entity.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class SellerCreationRequest {
    String shopName;
    String shopDescription;
    Double rating;
    LocalDateTime createdAt;
    String status;
}
