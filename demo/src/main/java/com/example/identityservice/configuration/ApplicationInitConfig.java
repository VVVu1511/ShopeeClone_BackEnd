package com.example.identityservice.configuration;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.User;
import com.example.identityservice.repository.RoleRepository;
// import com.example.identityservice.enums.Role;
import com.example.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;



@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;

	@Bean
	@ConditionalOnProperty(prefix = "spring", 
		value = "datasource.driverClassName",
		havingValue = "com.mysql.cj.jdbc.Driver"
	)	
	ApplicationRunner applicationRunner(UserRepository userRepository) {
		log.info("Init application......");
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				List<String> roles = new ArrayList<>();
				roles.add("ADMIN");

				User user = User.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin"))
						.roles(new HashSet<>(roleRepository.findAllByNameIn(roles)))
						.build();

				userRepository.save(user);
				log.warn("admin user has been created with default password: admin, please change it");
			}
		};
	}
}
