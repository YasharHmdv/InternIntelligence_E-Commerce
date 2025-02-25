package com.internproject.internintelligence_ecommerce.service.impl;

import java.util.Optional;

import com.internproject.internintelligence_ecommerce.config.UserInfoConfig;
import com.internproject.internintelligence_ecommerce.entity.User;
import com.internproject.internintelligence_ecommerce.exception.ResourceNotFoundException;
import com.internproject.internintelligence_ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		
		return user.map(UserInfoConfig::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
	}
}