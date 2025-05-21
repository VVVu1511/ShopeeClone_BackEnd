package com.example.identityservice.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.BuyingProductRequest;
import com.example.identityservice.dto.request.GettingProductRequest;
import com.example.identityservice.dto.request.ReviewProductRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.BuyingProductResponse;
import com.example.identityservice.dto.response.GettingProductResponse;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.entity.Cart;
import com.example.identityservice.entity.CartItem;
import com.example.identityservice.entity.CartItemId;
import com.example.identityservice.entity.Product;
import com.example.identityservice.entity.Review;
import com.example.identityservice.entity.Role;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.CartItemRepository;
import com.example.identityservice.repository.CartRepository;
import com.example.identityservice.repository.ProductRepository;
import com.example.identityservice.repository.ReviewRepository;
import com.example.identityservice.repository.RoleRepository;
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
	UserMapper userMapper;
	CartRepository cartRepository;
	PasswordEncoder passwordEncoder;
	ProductRepository productRepository;
	ReviewRepository reviewRepository;
	
	public UserResponse createUser(UserCreationRequest request) {
		log.info("Service: Create User");
		
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXIST);
		}
		
		User user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		HashSet<String> roles = new HashSet<>();
//		roles.add(Role.USER.name());
		
		
//		user.setRoles(roles);
		
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public UserResponse updateUser(Long userId,UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		userMapper.updateUser(user, request);
		userRepository.save(user);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		//find roles by name
		List<Role> roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));
		
		return userMapper.toUserResponse(user);
	}
	
	public UserResponse getMyInfo() {
		SecurityContext context = SecurityContextHolder.getContext();
		
		String name = context.getAuthentication().getName();
		
		User user = userRepository.findByUsername(name).orElseThrow(
				() -> new AppException(ErrorCode.USER_NOT_EXIST));
		
		return userMapper.toUserResponse(user);
	}
	
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getUsers(){
		log.info("In method get Users");
		return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
	}
	
	@PostAuthorize("returnObject.username == authentication.name")	
	public UserResponse getUser(Long userId) {
		log.info("In method get user by Id");
		return userMapper.toUserResponse(userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"))
				);
	}
	
	//mua san pham
	public BuyingProductResponse buyProduct(BuyingProductRequest request){
		try{
		
			Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
			
			Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST_CART));

			productRepository.deleteById(product.getProductId());

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

			productRepository.save(product);
		
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
						.user(userRepository.findById(request.getUserId()).orElseThrow())
						.product(productRepository.findById(request.getProductId()).orElseThrow())
						.createdAt(LocalDateTime.now())
						.comment(request.getComment())
						.rating(request.getRating())
						.build();

		return reviewRepository.save(review);
	}
}
