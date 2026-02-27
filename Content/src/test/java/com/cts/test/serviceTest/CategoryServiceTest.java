package com.cts.test.serviceTest;



import com.cts.dto.CategoryRequestDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.CategoryMapper;
import com.cts.model.Category;
import com.cts.repository.CategoryRepository;
import com.cts.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDTO requestDTO;
    private CategoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setCategoryId(1L);
        category.setName("Movies");
        category.setDescription("All movie titles");

        requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Movies");
        requestDTO.setDescription("All movie titles");

        responseDTO = new CategoryResponseDTO();
        responseDTO.setCategoryId(1L);
        responseDTO.setName("Movies");
        responseDTO.setDescription("All movie titles");
    }

    @Test
    void testCreateCategorySuccess() {
        when(categoryMapper.toEntity(requestDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.createCategory(requestDTO);

        assertNotNull(result);
        assertEquals("Movies", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryMapper.toDto(category)).thenReturn(responseDTO);

        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Movies", result.get(0).getName());
    }

    @Test
    void testDeleteCategorySuccess() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCategoryNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        GlobalException ex = assertThrows(GlobalException.class,
                () -> categoryService.deleteCategory(1L));

        assertEquals("Category not found with id: 1", ex.getMessage());
    }
}
