package com.cts.test.mapperTest;

import com.cts.dto.CategoryRequestDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.mapper.CategoryMapper;
import com.cts.mapper.CategoryMapperImpl;
import com.cts.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


public class CategoryMapperTest {


    private CategoryMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new CategoryMapperImpl();
    }// injected by Spring

    @Test
    public void testToEntity() {
        CategoryRequestDTO dto = new CategoryRequestDTO();
        dto.setName("Music");
        dto.setDescription("Music category");

        Category category = mapper.toEntity(dto);

        assertEquals("Music", category.getName());
        assertEquals("Music category", category.getDescription());
    }

    @Test
    public  void testToDto() {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setName("News");
        category.setDescription("News category");

        CategoryResponseDTO dto = mapper.toDto(category);

        assertEquals(1L, dto.getCategoryId());
        assertEquals("News", dto.getName());
        assertEquals("News category", dto.getDescription());
    }
}
//package com.cts.mapper;
//
//import com.cts.dto.CategoryRequestDTO;
//import com.cts.dto.CategoryResponseDTO;
//import com.cts.model.Category;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CategoryMapperTest {
//
//    private final CategoryMapper mapper = new CategoryMapperImpl(); // MapStruct auto-generated
//
//    @Test
//    void testToEntity() {
//        CategoryRequestDTO dto = new CategoryRequestDTO();
//        dto.setName("Music");
//        dto.setDescription("Music category");
//
//        Category category = mapper.toEntity(dto);
//
//        assertEquals("Music", category.getName());
//        assertEquals("Music category", category.getDescription());
//    }
//
//    @Test
//    void testToDto() {
//        Category category = new Category();
//        category.setCategoryId(1L);
//        category.setName("News");
//        category.setDescription("News category");
//
//        CategoryResponseDTO dto = mapper.toDto(category);
//
//        assertEquals(1L, dto.getCategoryId());
//        assertEquals("News", dto.getName());
//        assertEquals("News category", dto.getDescription());
//    }
//}
