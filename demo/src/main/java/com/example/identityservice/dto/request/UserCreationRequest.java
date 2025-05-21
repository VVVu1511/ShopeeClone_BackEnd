package com.example.identityservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.identityservice.validator.DobConstraints;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserCreationRequest {
	String username;
	String password;
	String firstName;
	String lastName;

	LocalDate dob;
	List<String> roles;
}
