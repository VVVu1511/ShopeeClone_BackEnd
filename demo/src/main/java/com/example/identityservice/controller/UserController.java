package com.example.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.identityservice.dto.request.BuyingProductRequest;
import com.example.identityservice.dto.request.GettingProductRequest;
import com.example.identityservice.dto.request.ReviewProductRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.ApiResponse;
import com.example.identityservice.dto.response.BuyingProductResponse;
import com.example.identityservice.dto.response.GettingProductResponse;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.Review;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class UserController {
	UserService userService;
	
	//tạo user mới
	@PostMapping
	ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
		
		return ApiResponse.<User>builder()
				.result(userService.createUser(request))
				.build();
	}
	
	//lấy tất cả user
	@GetMapping
	ApiResponse<List<User>> getUsers(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
		authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
		
		return ApiResponse.<List<User>>builder()
				.result(userService.getUsers())
				.build();
	}

	//mua sản phẩm
	@PostMapping("/buy/product")
	ApiResponse<BuyingProductResponse> buyProduct(@RequestBody BuyingProductRequest request){
		
		return ApiResponse.<BuyingProductResponse>builder()
			.result(userService.buyProduct(request))
			.build();
	}

	//lấy 1 user bất kì
	@GetMapping("/{userId}")
	ApiResponse<User> getUser(@PathVariable("userId") Long userId) {
		return ApiResponse.<User>builder()
				.result(userService.getUser(userId))
				.build();
	}
	
	//xem thông tin cá nhân
	@GetMapping("/myInfo")
	ApiResponse<User> getMyInfo() {
		return ApiResponse.<User>builder()
				.result(userService.getMyInfo())
				.build();
	}
	
	//cập nhật thông tin cá nhân
	@PutMapping("/{userId}")
	User updateUser(@PathVariable Long userId,@RequestBody @Valid UserUpdateRequest request) {
		return userService.updateUser(userId, request);
	}
	
	//hủy tài khoản
	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		
		return "User has been deleted";
	}
	
	//đánh giá sản phẩm
	@PostMapping("/reviewProduct")
	ApiResponse<Review> reviewOneProduct(@RequestBody @Valid ReviewProductRequest request) {
		
		return ApiResponse.<Review>builder()
			.result(userService.reviewProduct(request))
			.build();
	}
	
	
}
