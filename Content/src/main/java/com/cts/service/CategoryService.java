package com.cts.service;

import com.cts.dto.CategoryRequestDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.CategoryMapper;
import com.cts.model.Category;
import com.cts.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category cat = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(cat));
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new GlobalException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
