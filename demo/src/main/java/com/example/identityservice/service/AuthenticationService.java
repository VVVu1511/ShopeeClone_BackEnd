package com.example.identityservice.service;

import java.beans.IntrospectionException;
import java.security.PrivateKey;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.hibernate.usertype.LoggableUserType;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.request.LogoutRequest;
import com.example.identityservice.dto.request.RefreshRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.entity.InvalidatedToken;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.InvalidatedTokenRepository;
import com.example.identityservice.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	InvalidatedTokenRepository invalidatedTokenRepository;
	
	@NonFinal
	@Value("${jwt.signerKey}")
	protected String SIGNER_KEY;
	
	@NonFinal
	@Value("${jwt.valid-duration}")
	protected long VALID_DURATION;
	
	@NonFinal
	@Value("${jwt.refreshable-duration}")
	protected long REFRESHABLE_DURATION;
			
	public IntrospectResponse introspect(IntrospectRequest request)
			throws JOSEException, ParseException{
		String token = request.getToken();
		boolean isValid = true;
		
		try {
			verifyToken(token,false);
		} catch(AppException e) {
			isValid = false;
		}
		
		return IntrospectResponse.builder()
				.valid(isValid)				
				.build();
	}
	
	public void logout(LogoutRequest request) throws JOSEException, ParseException{
		try {
			SignedJWT signToken = verifyToken(request.getToken(),true);
			String jit = signToken.getJWTClaimsSet().getJWTID();
			Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
			
			InvalidatedToken invalidatedToken = InvalidatedToken.builder()
					.id(jit)
					.expiryTime(expiryTime)
					.build();
			
			invalidatedTokenRepository.save(invalidatedToken);
		} catch(AppException exception) {
			log.info("Token already expired");
		}
	}
	
	private SignedJWT verifyToken(String token, boolean isRefresh) 
			throws JOSEException, ParseException{
		JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
		
		SignedJWT signedJWT = SignedJWT.parse(token);
		
		Date expiryTime = (isRefresh) 
				? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli()) 
				: signedJWT.getJWTClaimsSet().getExpirationTime();
		
		boolean verified = signedJWT.verify(verifier);
	
		if(!verified && expiryTime.after(new Date()))
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		if(invalidatedTokenRepository
				.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
			throw new AppException(ErrorCode.UNAUTHORIZED);
		
		return signedJWT;
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
				
		boolean authenticated = passwordEncoder.matches(request.getPassword(), 
					user.getPassword());
		
		if(!authenticated) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}
		
		String tokenString = generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(tokenString)
				.authenticated(true)
				.build();
	}
	
	public AuthenticationResponse refreshToken(RefreshRequest request) 
			throws JOSEException, ParseException{
		SignedJWT signedJWT = verifyToken(request.getToken(),true);
		
		String jit = signedJWT.getJWTClaimsSet().getJWTID();
		Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
	
		InvalidatedToken invalidatedToken = InvalidatedToken.builder()
				.id(jit)
				.expiryTime(expiryTime)
				.build();
		
		invalidatedTokenRepository.save(invalidatedToken);
		
		String username = signedJWT.getJWTClaimsSet().getSubject();
		
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new AppException(ErrorCode.UNAUTHENTICATED));
		
		String tokenString = generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(tokenString)
				.authenticated(true)
				.build();
	}
	
	private String generateToken(User user) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		 
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issuer("Vu.com")
				.issueTime(new Date())
				.expirationTime(new Date(
						Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
				))
				.jwtID(UUID.randomUUID().toString())
				.claim("scope", buildScope(user))
				.build();
		
		Payload payload = new Payload(jwtClaimsSet.toJSONObject());
		
		JWSObject jwsObject = new JWSObject(header, payload);
		
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch(JOSEException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String buildScope(User user) {
		StringJoiner stringJoiner = new StringJoiner(" ");
		
		if(!org.springframework.util.CollectionUtils.isEmpty(user.getRoles())) {
			user.getRoles().forEach(role -> {
				stringJoiner.add("ROLE_" + role.getName());
				if(!CollectionUtils.isEmpty(role.getPermissions()))
					role.getPermissions()
						.forEach(permission -> stringJoiner.add(permission.getName()));
				
			});
		}
		
		return stringJoiner.toString();
	}
}
