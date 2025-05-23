package com.example.identityservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.identityservice.validator.DobConstraints;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String username;
	String password;
	
	String firstName;
	String lastName;
	
	@DobConstraints
	LocalDate dob;
	
	@Size(min = 8, max = 12)
	String phone;
    String address;
	String email;

	LocalDateTime createdAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
    Set<Cart> carts = new HashSet<>();

	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonIgnore
	Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	Set<Review> reviews = new HashSet<>();
}
