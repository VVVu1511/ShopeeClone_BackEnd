package com.example.identityservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	String username;
	String password;
	
	String firstName;
	String lastName;
	LocalDate dob;
	String phone;
    String address;
	String email;

	LocalDateTime createdAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Cart> carts = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Review> reviews = new HashSet<>();

	@ManyToMany
	Set<Role> roles; 
}
