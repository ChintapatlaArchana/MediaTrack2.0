package com.cts.test.mapperTest;

import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.mapper.TitleMapper;
import com.cts.mapper.TitleMapperImpl;
import com.cts.model.Category;
import com.cts.model.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class TitleMapperTest {


    private TitleMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new TitleMapperImpl();
    }

    @Test
    void testMapApplicationStatusFromString() {
        Title.ApplicationStatus status = mapper.mapApplicationStatus("available");
        assertEquals(Title.ApplicationStatus.available, status);
    }

    @Test
    void testMapApplicationStatusToString() {
        String status = mapper.mapApplicationStatus(Title.ApplicationStatus.coming_soon);
        assertEquals("coming_soon", status);
    }

    @Test
    void testEntityToDtoMapping() {
        Category category = new Category();
        category.setCategoryId(5L);

        Title title = new Title();
        title.setTitleId(10L);
        title.setName("Test Movie");
        title.setSynopsis("Synopsis");
        title.setGenre("Drama");
        title.setReleaseDate(LocalDate.of(2025, 1, 1));
        title.setRating("PG");
        title.setApplicationStatus(Title.ApplicationStatus.available);
        title.setCategory(category);

        TitleResponseDTO dto = mapper.toDto(title);

        assertEquals(10L, dto.getTitleId());
        assertEquals("Test Movie", dto.getName());
        assertEquals(5L, dto.getCategoryId());
        assertEquals(Title.ApplicationStatus.available, dto.getApplicationStatus());
    }

    @Test
    void testDtoToEntityMapping() {
        TitleRequestDTO dto = new TitleRequestDTO();
        dto.setName("Another Movie");
        dto.setSynopsis("Some synopsis");
        dto.setGenre("Action");
        dto.setReleaseDate(LocalDate.of(2026, 2, 1));
        dto.setRating("R");
        dto.setApplicationStatus(Title.ApplicationStatus.coming_soon);
        dto.setCategoryId(7L);

        Title entity = mapper.toEntity(dto);

        assertEquals("Another Movie", entity.getName());
        assertEquals("Action", entity.getGenre());
        assertEquals(Title.ApplicationStatus.coming_soon, entity.getApplicationStatus());
    }
}
