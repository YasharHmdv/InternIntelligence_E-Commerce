package com.internproject.internintelligence_ecommerce.service;

import com.internproject.internintelligence_ecommerce.dto.UserDTO;
import com.internproject.internintelligence_ecommerce.dto.UserResponse;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUser(Long userId);
}
