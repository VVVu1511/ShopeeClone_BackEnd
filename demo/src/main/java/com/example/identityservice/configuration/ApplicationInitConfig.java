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
import com.example.identityservice.entity.RoleUser;
import com.example.identityservice.entity.RoleUserId;
import com.example.identityservice.entity.User;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.RoleUserRepository;
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
	RoleUserRepository roleUserRepository;

	@Bean
	@ConditionalOnProperty(prefix = "spring", 
		value = "datasource.driverClassName",
		havingValue = "com.mysql.cj.jdbc.Driver"
	)	
	ApplicationRunner applicationRunner(UserRepository userRepository) {
		log.info("Init application......");
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User user = User.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin"))
						.build();
				
				Role role = roleRepository.findByName("ADMIN");

				roleUserRepository.save(RoleUser.builder()
					.id(RoleUserId.builder()
						.userId(user.getId())
						.roleId(role.getRoleId())
						.build())
					.role(role)
					.user(user)
					.build()
				);
				
				userRepository.save(user);
				log.warn("admin user has been created with default password: admin, please change it");
			}
		};
	}
}
