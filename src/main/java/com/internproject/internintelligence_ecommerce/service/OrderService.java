package com.internproject.internintelligence_ecommerce.service;

import com.internproject.internintelligence_ecommerce.dto.OrderDTO;
import com.internproject.internintelligence_ecommerce.dto.OrderResponse;

import java.util.List;


public interface OrderService {
	
	OrderDTO placeOrder(String emailId, Long cartId, String paymentMethod);
	
	OrderDTO getOrder(String emailId, Long orderId);
	
	List<OrderDTO> getOrdersByUser(String emailId);
	
	OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	OrderDTO updateOrder(String emailId, Long orderId, String orderStatus);
}