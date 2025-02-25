package com.internproject.internintelligence_ecommerce.controller;

import com.internproject.internintelligence_ecommerce.dto.LoginCredentials;
import com.internproject.internintelligence_ecommerce.dto.UserDTO;
import com.internproject.internintelligence_ecommerce.exception.UserNotFoundException;
import com.internproject.internintelligence_ecommerce.security.JWTUtil;
import com.internproject.internintelligence_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;


	private final JWTUtil jwtUtil;

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO user) throws UserNotFoundException {
		String encodedPass = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPass);

		UserDTO userDTO = userService.registerUser(user);

		String token = jwtUtil.generateToken(userDTO.getEmail());

		return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("jwt-token", token),
				HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public Map<String, Object> loginHandler(@Valid @RequestBody LoginCredentials credentials) {

		UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
				credentials.getEmail(), credentials.getPassword());

		authenticationManager.authenticate(authCredentials);

		String token = jwtUtil.generateToken(credentials.getEmail());

		return Collections.singletonMap("jwt-token", token);
	}
}