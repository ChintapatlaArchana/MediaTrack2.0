package com.cts.test.serviceTest;



import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.TitleMapper;
import com.cts.model.Category;
import com.cts.model.Title;
import com.cts.repository.CategoryRepository;
import com.cts.repository.TitleRepository;
import com.cts.service.TitleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TitleServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TitleRepository titleRepository;

    @Mock
    private TitleMapper titleMapper;

    @InjectMocks
    private TitleService titleService;

    public TitleServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTitleSuccess() {
        TitleRequestDTO dto = new TitleRequestDTO();
        dto.setName("Test Movie");
        dto.setCategoryId(1L);

        Category category = new Category();
        category.setCategoryId(1L);

        Title title = new Title();
        title.setName("Test Movie");
        title.setCategory(category);

        TitleResponseDTO responseDTO = new TitleResponseDTO();
        responseDTO.setName("Test Movie");

        when(titleMapper.toEntity(dto)).thenReturn(title);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(titleRepository.save(title)).thenReturn(title);
        when(titleMapper.toDto(title)).thenReturn(responseDTO);

        TitleResponseDTO result = titleService.createTitle(dto);

        assertEquals("Test Movie", result.getName());
        verify(titleRepository, times(1)).save(title);
    }

    @Test
    void testCreateTitleCategoryNotFound() {
        TitleRequestDTO dto = new TitleRequestDTO();
        dto.setCategoryId(99L);

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> titleService.createTitle(dto));
    }
}

