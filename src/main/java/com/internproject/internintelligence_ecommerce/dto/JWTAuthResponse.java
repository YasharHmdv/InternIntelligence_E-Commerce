package com.internproject.internintelligence_ecommerce.dto;

import lombok.Data;

@Data
public class JWTAuthResponse {
	private String token;
	
	private UserDTO user;
}