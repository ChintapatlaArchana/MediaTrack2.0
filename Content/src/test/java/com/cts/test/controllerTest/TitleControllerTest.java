package com.cts.test.controllerTest;



import com.cts.controller.TitleController;
import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.service.TitleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TitleControllerTest {

    @Mock
    private TitleService titleService;

    @InjectMocks
    private TitleController titleController;

    public TitleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTitle() {
        TitleRequestDTO requestDTO = new TitleRequestDTO();
        requestDTO.setName("Controller Test");

        TitleResponseDTO responseDTO = new TitleResponseDTO();
        responseDTO.setName("Controller Test");

        when(titleService.createTitle(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<TitleResponseDTO> response = titleController.createTitle(requestDTO);

        assertEquals("Controller Test", response.getBody().getName());
        verify(titleService, times(1)).createTitle(requestDTO);
    }

    @Test
    void testGetAllTitles() {
        TitleResponseDTO dto = new TitleResponseDTO();
        dto.setName("Movie1");

        when(titleService.getAllTitles()).thenReturn(List.of(dto));

        ResponseEntity<List<TitleResponseDTO>> response = titleController.ListTitles();

        assertEquals(1, response.getBody().size());
        assertEquals("Movie1", response.getBody().get(0).getName());
    }
}

