package com.internproject.internintelligence_ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.internproject.internintelligence_ecommerce.dto.CategoryDTO;
import com.internproject.internintelligence_ecommerce.dto.CategoryResponse;
import com.internproject.internintelligence_ecommerce.entity.Category;
import com.internproject.internintelligence_ecommerce.entity.Product;
import com.internproject.internintelligence_ecommerce.exception.APIException;
import com.internproject.internintelligence_ecommerce.exception.ResourceNotFoundException;
import com.internproject.internintelligence_ecommerce.repository.CategoryRepository;
import com.internproject.internintelligence_ecommerce.service.CategoryService;
import com.internproject.internintelligence_ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	
	private final ProductService productService;

	private final ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

		if (savedCategory != null) {
			throw new APIException("Category with the name '" + categoryDTO.getCategoryName() + "' already exists !!!");
		}
		Category map = modelMapper.map(categoryDTO, Category.class);
		savedCategory = categoryRepository.save(map);

		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		
		Page<Category> pageCategories = categoryRepository.findAll(pageDetails);

		List<Category> categories = pageCategories.getContent();

		if (categories.size() == 0) {
			throw new APIException("No category is created till now");
		}

		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());

		CategoryResponse categoryResponse = new CategoryResponse();
		
		categoryResponse.setContent(categoryDTOs);
		categoryResponse.setPageNumber(pageCategories.getNumber());
		categoryResponse.setPageSize(pageCategories.getSize());
		categoryResponse.setTotalElements(pageCategories.getTotalElements());
		categoryResponse.setTotalPages(pageCategories.getTotalPages());
		categoryResponse.setLastPage(pageCategories.isLast());
		
		return categoryResponse;
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
		Category savedCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Category map = modelMapper.map(categoryDTO, Category.class);
		savedCategory.setCategoryName(categoryDTO.getCategoryName());

		savedCategory = categoryRepository.save(savedCategory);

		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		List<Product> products = category.getProducts();

		products.forEach(product -> {
			productService.deleteProduct(product.getProductId());
		});
		
		categoryRepository.delete(category);

		return "Category with categoryId: " + categoryId + " deleted successfully !!!";
	}

}