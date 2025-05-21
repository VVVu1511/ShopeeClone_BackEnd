package com.example.identityservice.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.entity.Product;

@Mapper(
    componentModel = "spring"
)
public interface ProductMapper {
	@Mapping(target = "productId", ignore = true)
	
	void updateProduct(@MappingTarget Product product, UpdatingProductRequest request);

	@Mappings(
		{
			@Mapping(target = "productId",ignore = true),
			@Mapping(target = "name", ignore = true),
			@Mapping(target = "description", ignore = true),
			@Mapping(target = "price", ignore = true),
			@Mapping(target = "createdAt", ignore = true)
		}
	)
	void updateProduct2(@MappingTarget Product product, UpdatingProductRequest request);
}
