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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerCreationRequest {
    String shopName;
    String shopDescription;
}
