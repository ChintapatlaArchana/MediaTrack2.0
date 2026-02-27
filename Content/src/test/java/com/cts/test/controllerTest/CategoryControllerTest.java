package com.cts.test.controllerTest;

import com.cts.controller.CategoryController;
import com.cts.dto.CategoryRequestDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryRequestDTO requestDTO;
    private CategoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Sports");
        requestDTO.setDescription("Sports category");

        responseDTO = new CategoryResponseDTO();
        responseDTO.setCategoryId(1L);
        responseDTO.setName("Sports");
        responseDTO.setDescription("Sports category");
    }

    @Test
    void testCreateCategory() {
        when(categoryService.createCategory(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<CategoryResponseDTO> result = categoryController.createCategory(requestDTO);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Sports", result.getBody().getName());
    }

    @Test
    void testListCategories() {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(responseDTO));

        ResponseEntity<List<CategoryResponseDTO>> result = categoryController.listCategories();

        assertEquals(1, result.getBody().size());
        assertEquals("Sports", result.getBody().get(0).getName());
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1L);

        ResponseEntity<Void> result = categoryController.deleteCategory(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(categoryService, times(1)).deleteCategory(1L);
    }
}
