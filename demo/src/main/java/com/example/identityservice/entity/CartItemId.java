package com.example.identityservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CartItemId implements Serializable {
    @Column(name = "cart_id", length = 36)
    String cartId;
    @Column(name = "product_id", length = 36)
    String productId;
}

