package com.cts.Test.controller;

import com.cts.controller.PlaybackController;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.model.PlaybackSession;
import com.cts.service.PlaybackService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlaybackController.class)
class PlaybackControllerTest {

    @Autowired private MockMvc mockMvc;

    // ✅ Mockito mock registered in Spring test context
    @MockBean private PlaybackService playbackService;

    // Small helper to set fields on DTOs that have no setters
    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    @DisplayName("POST /playback -> 201 (uses Mockito stubbing)")
    void createPlayback_noSetters_usesMockito() throws Exception {
        // Request JSON (request DTO has setters or not — doesn't matter)
        String requestJson = """
            { "status": "ACTIVE", "assetId": 100 }
        """;

        // Build response DTO via reflection (no setters needed)
        PlaybackSessionResponseDTO resp = new PlaybackSessionResponseDTO();
        setField(resp, "id", 1L);
        setField(resp, "status", PlaybackSession.Status.ACTIVE);
        setField(resp, "userId", 10L);
        setField(resp, "assetId", 100L);

        // ✅ Mockito stubbing
        Mockito.when(playbackService.create(any(PlaybackSessionRequestDTO.class), eq("10")))
                .thenReturn(resp);

        mockMvc.perform(post("/playback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", "10")
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.assetId").value(100));
    }

    @Test
    @DisplayName("GET /playback -> 200 (uses Mockito stubbing)")
    void getAll_noSetters_usesMockito() throws Exception {
        PlaybackSessionResponseDTO r1 = new PlaybackSessionResponseDTO();
        setField(r1, "id", 1L);
        setField(r1, "status", PlaybackSession.Status.ACTIVE);
        setField(r1, "userId", 10L);
        setField(r1, "assetId", 100L);

        PlaybackSessionResponseDTO r2 = new PlaybackSessionResponseDTO();
        setField(r2, "id", 2L);
        // Use an enum that exists in your code (e.g., STOPPED if PAUSED doesn't exist)
        setField(r2, "status", PlaybackSession.Status.COMPLETED);
        setField(r2, "userId", 11L);
        setField(r2, "assetId", 101L);

        // ✅ Mockito stubbing
        Mockito.when(playbackService.getAll()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/playback"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].status").value("COMPLETED"));
    }

    @Test
    @DisplayName("DELETE /playback/{id} -> 204 (uses Mockito verify)")
    void deleteById_usesMockitoVerify() throws Exception {
        mockMvc.perform(delete("/playback/5"))
                .andExpect(status().isNoContent());

        // ✅ Mockito verification
        Mockito.verify(playbackService).delete(5L);
    }
}
