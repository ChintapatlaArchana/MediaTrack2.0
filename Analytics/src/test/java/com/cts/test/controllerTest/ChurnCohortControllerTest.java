package com.cts.test.controllerTest;

import com.cts.controller.ChurnCohortController;
import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.service.ChurnCohortService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ChurnCohortControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChurnCohortService churnCohortService;

    @InjectMocks
    private ChurnCohortController churnCohortController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(churnCohortController).build();
    }

    @Test
    void testGenerateChurnCohort_Success() throws Exception {
        ChurnCohortRequestDTO requestDTO = new ChurnCohortRequestDTO();
        ChurnCohortResponseDTO responseDTO = new ChurnCohortResponseDTO();
        responseDTO.setCohortId(1L);

        when(churnCohortService.generateChurnCohort(any(ChurnCohortRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/churnCohort")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cohortId").value(1));
    }

    @Test
    void testListChurnCohorts_Success() throws Exception {
        ChurnCohortResponseDTO responseDTO = new ChurnCohortResponseDTO();
        responseDTO.setCohortId(1L);
        List<ChurnCohortResponseDTO> list = Collections.singletonList(responseDTO);

        when(churnCohortService.getAllChurnCohorts()).thenReturn(list);

        mockMvc.perform(get("/churnCohort"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cohortId").value(1));
    }

    @Test
    void testDeleteChurnCohort_Success() throws Exception {
        doNothing().when(churnCohortService).deleteChurnCohort(anyLong());

        mockMvc.perform(delete("/churnCohort/1"))
                .andExpect(status().isNoContent());
    }
}
