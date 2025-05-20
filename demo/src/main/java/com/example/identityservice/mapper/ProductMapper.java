package com.example.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.entity.Product;

@Mapper(
    componentModel = "spring"
)
public interface ProductMapper {
    @Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "productId", ignore = true)
	void updateProduct(@MappingTarget Product product, UpdatingProductRequest request);
}
