package com.cts.test.repositoryTest;

import com.cts.model.AdImpression;
import com.cts.repository.AdImpressionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // optional (AssertJ)
import static org.junit.jupiter.api.Assertions.*;          // JUnit assertions
import static org.mockito.Mockito.*;

class AdImpressionRepositoryTest {

    // Mock the repository interface
    private final AdImpressionRepository repo = mock(AdImpressionRepository.class);

    @Test
    void save_shouldReturnSavedEntity() {
        // Arrange
        AdImpression impression = new AdImpression();
        // If your entity has fields, set them here as needed:
        // impression.setSessionId(777L);

        when(repo.save(impression)).thenReturn(impression);

        // Act
        AdImpression saved = repo.save(impression);

        // Assert
        assertNotNull(saved);
        assertEquals(impression, saved);
        verify(repo, times(1)).save(impression);
    }

    @Test
    void findById_shouldReturnOptionalWithEntity() {
        // Arrange
        Long id = 1L;
        AdImpression impression = new AdImpression();
        when(repo.findById(id)).thenReturn(Optional.of(impression));

        // Act
        Optional<AdImpression> found = repo.findById(id);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(impression, found.get());
        verify(repo).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenNotFound() {
        // Arrange
        Long id = 999L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<AdImpression> found = repo.findById(id);

        // Assert
        assertTrue(found.isEmpty());
        verify(repo).findById(id);
    }

    @Test
    void findAll_shouldReturnListOfEntities() {
        // Arrange
        AdImpression i1 = new AdImpression();
        AdImpression i2 = new AdImpression();
        List<AdImpression> mocked = Arrays.asList(i1, i2);

        when(repo.findAll()).thenReturn(mocked);

        // Act
        List<AdImpression> result = repo.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result).containsExactly(i1, i2); // if using AssertJ
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
        AdImpression impression = new AdImpression();

        when(repo.save(any(AdImpression.class))).thenReturn(impression);

        ArgumentCaptor<AdImpression> captor = ArgumentCaptor.forClass(AdImpression.class);

        // Act
        repo.save(impression);

        // Assert
        verify(repo).save(captor.capture());
        AdImpression passed = captor.getValue();
        assertNotNull(passed);
        // You can assert fields if you set any above, e.g.:
        // assertEquals(777L, passed.getSessionId());
    }
}
