package com.example.identityservice.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.User;

@Mapper(
	componentModel = "spring"
)
public interface UserMapper {
	User toUser(UserCreationRequest request);

	UserResponse toUserResponse(User user);
	
	void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
