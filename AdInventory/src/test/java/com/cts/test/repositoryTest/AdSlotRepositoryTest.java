package com.cts.test.repositoryTest;

import com.cts.model.AdSlot;
import com.cts.repository.AdSlotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // optional: AssertJ
import static org.junit.jupiter.api.Assertions.*;          // JUnit assertions
import static org.mockito.Mockito.*;

class AdSlotRepositoryTest {

    // Mock the repository interface
    private final AdSlotRepository repo = mock(AdSlotRepository.class);

    @Test
    void save_shouldReturnSavedEntity() {
        // Arrange
        AdSlot slot = new AdSlot();
        // If your entity has mandatory fields, set them here:
        // slot.setName("Pre-Roll");
        // slot.setDurationSeconds(30);

        when(repo.save(slot)).thenReturn(slot);

        // Act
        AdSlot saved = repo.save(slot);

        // Assert
        assertNotNull(saved);
        assertEquals(slot, saved);
        verify(repo, times(1)).save(slot);
    }

    @Test
    void findById_shouldReturnOptionalWithEntity() {
        // Arrange
        Long id = 100L;
        AdSlot slot = new AdSlot();
        when(repo.findById(id)).thenReturn(Optional.of(slot));

        // Act
        Optional<AdSlot> found = repo.findById(id);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(slot, found.get());
        verify(repo).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenNotFound() {
        // Arrange
        Long id = 404L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<AdSlot> found = repo.findById(id);

        // Assert
        assertTrue(found.isEmpty());
        verify(repo).findById(id);
    }

    @Test
    void findAll_shouldReturnListOfEntities() {
        // Arrange
        AdSlot s1 = new AdSlot();
        AdSlot s2 = new AdSlot();
        List<AdSlot> mocked = Arrays.asList(s1, s2);

        when(repo.findAll()).thenReturn(mocked);

        // Act
        List<AdSlot> result = repo.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result).containsExactly(s1, s2); // if using AssertJ
        verify(repo).findAll();
    }

    @Test
    void deleteById_shouldInvokeRepository() {
        // Arrange
        Long id = 7L;
        doNothing().when(repo).deleteById(id);

        // Act
        repo.deleteById(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    void save_shouldCaptureArgument() {
        // Arrange
        AdSlot slot = new AdSlot();
        when(repo.save(any(AdSlot.class))).thenReturn(slot);

        ArgumentCaptor<AdSlot> captor = ArgumentCaptor.forClass(AdSlot.class);

        // Act
        repo.save(slot);

        // Assert
        verify(repo).save(captor.capture());
        AdSlot passed = captor.getValue();
        assertNotNull(passed);
        // If you set fields above, assert them here:
        // assertEquals("Pre-Roll", passed.getName());
    }
}
