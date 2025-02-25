package com.internproject.internintelligence_ecommerce.dto;

import lombok.Data;

@Data
public class JWTAuthRequest {
	private String username;  // email
	private String password;
}