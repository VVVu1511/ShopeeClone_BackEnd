package com.example.identityservice.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.example.identityservice.entity.Cart;
import com.example.identityservice.entity.Product;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreatingCategoryRequest {
        String name;
}
