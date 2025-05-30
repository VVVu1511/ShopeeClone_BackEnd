package com.example.identityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import lombok.Getter;


@Getter
public enum ErrorCode {
	INVALID_KEY(1001, "Invalid message key!", HttpStatus.BAD_REQUEST),
	UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception",HttpStatus.INTERNAL_SERVER_ERROR),
	USER_EXIST(1002,"User exists", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
	USER_NOT_EXIST(1005, "User not exist", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
	INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
	PRODUCT_NOT_EXIST(1009, "Product doesn't exist", HttpStatus.BAD_REQUEST),
	NOT_ENOUGH_QUANTITY(1010,"The stock quantity is not enough", HttpStatus.BAD_REQUEST),
	NOT_EXIST_CART(1011,"The cart doesn't exist", HttpStatus.BAD_REQUEST),
	NOT_EXIST_CATEGORY(1012,"The category doesn't exist", HttpStatus.BAD_REQUEST),
	NOT_EXIST(1013,"Not exist", HttpStatus.BAD_REQUEST),
	EMAIL_EXIST(1014,"Email already exists", HttpStatus.BAD_REQUEST),
	PHONE_NUMBER_EXIST(1015,"Phone number already exists", HttpStatus.BAD_REQUEST),
	PERMISSION_EXIST(1016,"Permission already exists", HttpStatus.BAD_REQUEST),
	ROLE_NOT_EXIST(1017,"Role doesn't exist", HttpStatus.BAD_REQUEST),
	PERMISSION_NOT_EXIST(1018,"Permission doesn't exist", HttpStatus.BAD_REQUEST),
	;

	
	private int code;
	private String message;
	private HttpStatusCode statusCode;
	
	private ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
