package com.internproject.internintelligence_ecommerce.service.impl;

import com.internproject.internintelligence_ecommerce.dto.*;
import com.internproject.internintelligence_ecommerce.entity.*;
import com.internproject.internintelligence_ecommerce.repository.AddressRepository;
import com.internproject.internintelligence_ecommerce.repository.UserRepository;
import com.internproject.internintelligence_ecommerce.service.CartService;
import com.internproject.internintelligence_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.internproject.internintelligence_ecommerce.config.AppConstants;


import com.internproject.internintelligence_ecommerce.exception.APIException;
import com.internproject.internintelligence_ecommerce.exception.ResourceNotFoundException;
import com.internproject.internintelligence_ecommerce.repository.RoleRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final AddressRepository addressRepository;
	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final ModelMapper modelMapper;

	private final CartService cartService;
	@Override
	@Transactional
	public UserDTO registerUser(UserDTO userDTO) {

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new APIException("User already exists with emailId: " + userDTO.getEmail());
		}
		try {
			String encodedPass = passwordEncoder.encode(userDTO.getPassword());
			userDTO.setPassword(encodedPass);

			User user = modelMapper.map(userDTO, User.class);

			Cart cart = new Cart();
			user.setCart(cart);

			Role role = roleRepository.findById(AppConstants.USER_ID).orElseThrow(()->
			new RuntimeException("Role not found"));
			if (!user.getRoles().contains(role)) {
				user.getRoles().add(role);
			}

			String country = userDTO.getAddress().getCountry();
			String state = userDTO.getAddress().getState();
			String city = userDTO.getAddress().getCity();
			String pincode = userDTO.getAddress().getPincode();
			String street = userDTO.getAddress().getStreet();
			String buildingName = userDTO.getAddress().getBuildingName();

			Address address = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
					city, pincode, street, buildingName);

			if (address == null) {
				address = new Address(country, state, city, pincode, street, buildingName);
				address = addressRepository.save(address);
			}

			user.setAddresses(List.of(address));

			User registeredUser = userRepository.save(user);

			cart.setUser(registeredUser);

			userDTO = modelMapper.map(registeredUser, UserDTO.class);

			userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

			return userDTO;
		} catch (DataIntegrityViolationException e) {
			throw new APIException("Error during user registration: " + e.getMessage());

		}

	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		
		Page<User> pageUsers = userRepository.findAll(pageDetails);
		
		List<User> users = pageUsers.getContent();

		if (users.size() == 0) {
			throw new APIException("No User exists !!!");
		}

		List<UserDTO> userDTOs = users.stream().map(user -> {
			UserDTO dto = modelMapper.map(user, UserDTO.class);

			if (user.getAddresses().size() != 0) {
				dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
			}

			CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

			List<ProductDTO> products = user.getCart().getCartItems().stream()
					.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());


			return dto;

		}).collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();
		
		userResponse.setContent(userDTOs);
		userResponse.setPageNumber(pageUsers.getNumber());
		userResponse.setPageSize(pageUsers.getSize());
		userResponse.setTotalElements(pageUsers.getTotalElements());
		userResponse.setTotalPages(pageUsers.getTotalPages());
		userResponse.setLastPage(pageUsers.isLast());
		
		return userResponse;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		UserDTO userDTO = modelMapper.map(user, UserDTO.class);

		userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

		CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

		List<ProductDTO> products = user.getCart().getCartItems().stream()
				.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());


		return userDTO;
	}

	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		String encodedPass = passwordEncoder.encode(userDTO.getPassword());

		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setPassword(encodedPass);

		if (userDTO.getAddress() != null) {
			String country = userDTO.getAddress().getCountry();
			String state = userDTO.getAddress().getState();
			String city = userDTO.getAddress().getCity();
			String pincode = userDTO.getAddress().getPincode();
			String street = userDTO.getAddress().getStreet();
			String buildingName = userDTO.getAddress().getBuildingName();

			Address address = addressRepository.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
					city, pincode, street, buildingName);

			if (address == null) {
				address = new Address(country, state, city, pincode, street, buildingName);

				address = addressRepository.save(address);

				user.setAddresses(List.of(address));
			}
		}

		userDTO = modelMapper.map(user, UserDTO.class);

		userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

		CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

		List<ProductDTO> products = user.getCart().getCartItems().stream()
				.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

		return userDTO;
	}

	@Override
	public String deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		List<CartItem> cartItems = user.getCart().getCartItems();
		Long cartId = user.getCart().getCartId();

		cartItems.forEach(item -> {

			Long productId = item.getProduct().getProductId();

			cartService.deleteProductFromCart(cartId, productId);
		});

		userRepository.delete(user);

		return "User with userId " + userId + " deleted successfully!!!";
	}

}