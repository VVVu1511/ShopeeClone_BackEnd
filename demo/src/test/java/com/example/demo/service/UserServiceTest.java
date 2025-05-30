// package com.example.demo.service;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;
// import java.util.Optional;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.TestPropertySource;

// import com.example.identityservice.dto.request.UserCreationRequest;
// import com.example.identityservice.dto.response.UserResponse;
// import com.example.identityservice.entity.User;
// import com.example.identityservice.exception.AppException;
// import com.example.identityservice.repository.UserRepository;
// import com.example.identityservice.service.UserService;
// import static org.mockito.Mockito.when;

// @SpringBootTest
// @TestPropertySource("/test.properties")
// public class UserServiceTest {
// 	@Autowired
// 	private UserService userService;
	
// 	@MockBean
// 	private UserRepository userRepository;
// 	private UserCreationRequest request;
// 	private UserResponse userResponse;
// 	private User user;
// 	private LocalDate dob;
	
// 	@BeforeEach
// 	void initData() {
// 		dob = LocalDate.of(1990, 1, 1);
		
// 		request = UserCreationRequest.builder()
// 				.username("john")
// 				.firstName("David")
// 				.lastName("Laid")
// 				.password("12345678")
// 				.dob(dob)
// 				.build();
		
// 		userResponse = UserResponse.builder()
// 				//need to paste real ID to test
// 				// .id("cf0600f538b3")
// 				.username("john")
// 				.firstName("David")
// 				.lastName("Laid")
// 				.dob(dob)
// 				.build();
		
// 		user = User.builder()
// 				// .id(cf0600f538b3")
// 				.username("john")
// 				.firstName("David")
// 				.lastName("Laid")
// 				.dob(dob)
// 				.build();
				
// 	}
	
// 	@Test
// 	void createUser_validRequest_success() {
// 		//GIVEN
// 		when(userRepository.existsByUsername(anyString()))
// 			.thenReturn(false);
		
// 		when(userRepository.save(any())).thenReturn(user);
		
// 		//WHEN
// 		UserResponse response = userService.createUser(request);
		
// 		//THEN
// 		Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
// 		Assertions.assertThat(response.getUsername()).isEqualTo("john");
// 	}
	
// 	@Test
// 	void createUser_userExisted_fail() {
// 		//GIVEN
// 		when(userRepository.existsByUsername(anyString()))
// 			.thenReturn(true);
		
// 		//WHEN
// 		AppException exception = assertThrows(AppException.class, 
// 				() -> userService.createUser(request));
		
// 		Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1234);
// 	}
	
// 	@Test
// 	@WithMockUser(username = "john")
// 	void getMyInfo_valid_success() {
// 		when(userRepository.findByUsername(anyString()))
// 			.thenReturn(Optional.of(user));
		
// 		UserResponse response = userService.getMyInfo();
		
// 		Assertions.assertThat(response.getUsername()).isEqualTo("john");
// 		Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
// 	}
	
// 	@Test
// 	@WithMockUser(username = "john")
// 	void getMyInfo_userNotFound_error() {
// 		when(userRepository.findByUsername(anyString()))
// 			.thenReturn(Optional.ofNullable(null));
		
// 		AppException exception = assertThrows(AppException.class, 
// 				() -> userService.getMyInfo());
		
// 		Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo("1005");
// 	}
// }
