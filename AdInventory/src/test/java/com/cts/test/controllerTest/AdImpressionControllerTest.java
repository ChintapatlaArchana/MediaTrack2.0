    package com.cts.test.controllerTest;

    import com.cts.controller.AdImpressionController;
    import com.cts.dto.AdImpressionRequestDTO;
    import com.cts.dto.AdImpressionResponseDTO;
    import com.cts.service.AdImpressionService;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.http.ResponseEntity;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    class AdImpressionControllerTest {

        @Mock
        private AdImpressionService service;

        @InjectMocks
        private AdImpressionController controller;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreate() {
            // Arrange: build request with IDs in the JSON body fields
            AdImpressionRequestDTO request = new AdImpressionRequestDTO();
            request.setCampaignId(10L);
            request.setSlotId(5L);
            request.setSessionId(777L);
            // set other optional fields if you like:
            // request.setTimestamp(LocalDateTime.now());
            // request.setViewability(Viewability.VISIBLE);
            // request.setClicked(Clicked.NO);

            AdImpressionResponseDTO expected = new AdImpressionResponseDTO();

            // The controller will pass IDs from the body to service.create(...)
            when(service.create(
                    request.getCampaignId(),
                    request.getSlotId(),
                    request.getSessionId(),
                    request
            )).thenReturn(expected);

            // Act: call controller with only the request body
            ResponseEntity<AdImpressionResponseDTO> response = controller.create(request);

            // Assert
            assertEquals(expected, response.getBody());

            // Verify
            verify(service).create(
                    request.getCampaignId(),
                    request.getSlotId(),
                    request.getSessionId(),
                    request
            );
        }







        @Test
        void testGetAll() {
            when(service.getAll()).thenReturn(java.util.List.of(new AdImpressionResponseDTO()));

            var result = controller.getAll();

            assertNotNull(result.getBody());
            assertEquals(1, result.getBody().size());
        }

        @Test
        void testGetById() {
            AdImpressionResponseDTO dto = new AdImpressionResponseDTO();
            dto.setImpressionId(5L);

            when(service.getById(5L)).thenReturn(dto);

            var result = controller.getById(5L);

            assertEquals(5L, result.getBody().getImpressionId());
        }

        @Test
        void testDelete() {
            when(service.delete(1L)).thenReturn("deleted");

            var result = controller.delete(1L);

            assertEquals("deleted", result.getBody());
            verify(service).delete(1L);
        }
    }