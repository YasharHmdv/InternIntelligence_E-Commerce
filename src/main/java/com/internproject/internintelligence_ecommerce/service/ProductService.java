package com.internproject.internintelligence_ecommerce.service;

import java.io.IOException;

import com.internproject.internintelligence_ecommerce.dto.ProductDTO;
import com.internproject.internintelligence_ecommerce.dto.ProductResponse;
import com.internproject.internintelligence_ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
									 String sortOrder);

	ProductDTO updateProduct(Long productId, Product product);

	ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

	ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder);

	String deleteProduct(Long productId);

}