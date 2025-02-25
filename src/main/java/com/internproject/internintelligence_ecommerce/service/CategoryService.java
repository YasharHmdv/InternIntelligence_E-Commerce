package com.internproject.internintelligence_ecommerce.service;

import com.internproject.internintelligence_ecommerce.dto.CategoryDTO;
import com.internproject.internintelligence_ecommerce.dto.CategoryResponse;
import com.internproject.internintelligence_ecommerce.entity.Category;

public interface CategoryService {

	CategoryDTO createCategory(Category category);

	CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	CategoryDTO updateCategory(Category category, Long categoryId);

	String deleteCategory(Long categoryId);
}