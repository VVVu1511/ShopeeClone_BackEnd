package com.example.identityservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(length = 36)
    String productId;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    String name;
    String description;
    Double price;
    Integer stockQty;
    LocalDateTime createdAt;
    String status;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    Set<ProductImage> images = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    Set<OrderItem> orderItems = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    Set<CartItem> cartItems = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    Set<Wishlist> wishlists = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    Set<ProductPromo> promotions = new HashSet<>();
}

