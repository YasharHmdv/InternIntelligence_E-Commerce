package com.internproject.internintelligence_ecommerce.service;

import com.internproject.internintelligence_ecommerce.dto.CartDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CartService {
	
	CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);
	
	List<CartDTO> getAllCarts();
	
	CartDTO getCart(String emailId, Long cartId);
	
	CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);
	
	void updateProductInCarts(Long cartId, Long productId);
	
	String deleteProductFromCart(Long cartId, Long productId);

    ResponseEntity<CartDTO> createCart(CartDTO cartDTO);
}