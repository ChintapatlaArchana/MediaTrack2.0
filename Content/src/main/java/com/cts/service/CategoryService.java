package com.cts.service;

import com.cts.dto.CategoryRequestDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.mapper.CategoryMapper;
import com.cts.model.Category;
import com.cts.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto){
        Category cat = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(cat));
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public void deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Category not found with id: "+id);
        }
        categoryRepository.deleteById(id);
    }


}