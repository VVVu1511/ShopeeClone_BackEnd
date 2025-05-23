package com.example.identityservice.dto.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateRoleRequest {
    Long roleId;
	String name;
	String description;  
}
