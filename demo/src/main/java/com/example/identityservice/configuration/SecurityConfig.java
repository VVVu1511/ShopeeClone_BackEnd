package com.example.identityservice.configuration;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
	private final String[] PUBLIC_ENDPOINT = {"/users",
			"/auth/token",
			"/auth/introspect",
			"/auth/logout",
			"/auth/refresh"
			};
	
	
	@Autowired
	private CustomJwtDecoder customJwtDecoder;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// httpSecurity.authorizeHttpRequests(request -> 
		// 			request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
		// 			.anyRequest().authenticated())
		httpSecurity.authorizeHttpRequests(request -> 
					request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
					.anyRequest().anonymous());

		httpSecurity.oauth2ResourceServer(oauth2 -> 
			oauth2.jwt(JwtConfigurer -> 
				JwtConfigurer.decoder(customJwtDecoder)
				.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
		);

		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		return httpSecurity.build();
    }
	
	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter(); 
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		
		return jwtAuthenticationConverter;
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}

