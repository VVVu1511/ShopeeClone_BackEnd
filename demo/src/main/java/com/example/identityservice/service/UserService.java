package com.example.identityservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.BuyingProductRequest;
import com.example.identityservice.dto.request.CartUpdateRequest;
import com.example.identityservice.dto.request.GettingProductRequest;
import com.example.identityservice.dto.request.ReviewProductRequest;
import com.example.identityservice.dto.request.UpdatingProductRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.BuyingProductResponse;
import com.example.identityservice.dto.response.GettingProductResponse;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.enums.RoleDefined;
import com.example.identityservice.entity.Cart;
import com.example.identityservice.entity.CartItem;
import com.example.identityservice.entity.CartItemId;
import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.Review;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.RoleUser;
import com.example.identityservice.entity.RoleUserId;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.CartMapper;
import com.example.identityservice.mapper.ProductMapper;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.CartItemRepository;
import com.example.identityservice.repository.CartRepository;
import com.example.identityservice.repository.ProductRepository;
import com.example.identityservice.repository.ReviewRepository;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.RoleUserRepository;
import com.example.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	UserRepository userRepository;
	RoleRepository roleRepository;
	CartItemRepository cartItemRepository;
	CartRepository cartRepository;
	ProductRepository productRepository;
	ReviewRepository reviewRepository;
	RoleUserRepository roleUserRepository;
	
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	ProductMapper productMapper;
	CartMapper cartMapper;

	public void addRolesForUser(List<String> roles_string, User user){
		List<Role> roles = roleRepository.findAllByNameIn(roles_string);

		roleUserRepository.saveAll(
			roles.stream().map(
			role -> RoleUser.builder()
				.role(role)
				.user(user)
				.id(RoleUserId.builder()
					.role(role.getRoleId())
					.user(user.getId())
					.build())
				.build()
		).toList());
	}

	public User createUser(UserCreationRequest request) {		
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXIST);
		}

		if(userRepository.existsByEmail(request.getEmail())){
			throw new AppException(ErrorCode.EMAIL_EXIST);
		}
		
		if(userRepository.existsByPhone(request.getPhone())){
			throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
		}

		User user = userMapper.toUser(request);
		user.setCreatedAt(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		List<String> roles = new ArrayList<>();
		roles.add("USER");
		addRolesForUser(roles, user);

		return userRepository.save(user);
	}
	
	public User updateUser(Long userId,UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		userMapper.updateUser(user, request);
		userRepository.save(user);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		//find roles by name
		addRolesForUser(request.getRoles(), user);
		
		return user;
	}
	
	public User getMyInfo() {
		SecurityContext context = SecurityContextHolder.getContext();
		
		String name = context.getAuthentication().getName();
		
		User user = userRepository.findByUsername(name).orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_EXIST));
		
		return user;
	}
	
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

		userRepository.deleteById(userId);
		roleUserRepository.deleteByUserId(userId);
		cartRepository.deleteByUser(user);
		reviewRepository.deleteByUser(user);
	}
	
	// @PreAuthorize("hasRole('ADMIN')")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	// @PostAuthorize("returnObject.username == authentication.name")	
	public User getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
	}
	
	//mua san pham
	public BuyingProductResponse buyProduct(BuyingProductRequest request){
		try{
		
			Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
			
			Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST_CART));
			
			cart.setUpdatedAt(LocalDateTime.now());

			if(product.getStockQty() < request.getQuantity()){
				throw new AppException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}

			cartItemRepository.save(CartItem.builder()
								.id(CartItemId.builder()
									.cartId(request.getCartId())
									.productId(product.getProductId())
									.build())
								.product(product)
								.cart(cart)
								.quantity(request.getQuantity())
								.addedAt(LocalDateTime.now())
								.build()
						);

			product.setStockQty(product.getStockQty() - request.getQuantity());

			productMapper.updateProduct2(product, UpdatingProductRequest.builder()
				.stockQty(product.getStockQty() - request.getQuantity())
				.build()
			);

			productRepository.save(product);

			cartMapper.updateCart(cart, CartUpdateRequest.builder()
				.updatedAt(LocalDateTime.now()).build());

			cartRepository.save(cart);
		
		} catch(Exception e){
			throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
		}

		return BuyingProductResponse.builder()
			.success(true)
			.build();
	}

	//danh gia san pham
	public Review reviewProduct(ReviewProductRequest request) {
		Review review = Review.builder()
						.user(userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST)))
						.product(productRepository.findById(request.getProductId()).orElseThrow())
						.createdAt(LocalDateTime.now())
						.comment(request.getComment())
						.rating(request.getRating())
						.build();

		return reviewRepository.save(review);
	}
}
