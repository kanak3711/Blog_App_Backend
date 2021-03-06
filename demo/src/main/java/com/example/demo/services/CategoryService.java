package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.payloads.CategoryDto;



@Service
public interface CategoryService {
	
 public CategoryDto createCategory(CategoryDto categoryDto);
 public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
 public void deleteCategory(Integer categoryId);
 public CategoryDto getCategory(Integer categoryId);
 public List<CategoryDto> getCategories();
	

}
