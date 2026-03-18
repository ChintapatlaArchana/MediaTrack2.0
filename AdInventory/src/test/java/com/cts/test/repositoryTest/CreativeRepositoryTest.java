package com.cts.test.repositoryTest;

import com.cts.model.Creative;
import com.cts.repository.CreativeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // optional: AssertJ for fluent assertions
import static org.junit.jupiter.api.Assertions.*;          // JUnit assertions
import static org.mockito.Mockito.*;

class CreativeRepositoryTest {

    // Mock the repository interface
    private final CreativeRepository repo = mock(CreativeRepository.class);

    @Test
    void save_shouldReturnSavedEntity() {
        // Arrange
        Creative creative = new Creative();
        // If your entity has mandatory fields, set them here:
        // creative.setName("Banner A");
        // creative.setFormat("IMAGE");
        // creative.setUrl("https://cdn.example.com/banner-a.png");

        when(repo.save(creative)).thenReturn(creative);

        // Act
        Creative saved = repo.save(creative);

        // Assert
        assertNotNull(saved);
        assertEquals(creative, saved);
        verify(repo, times(1)).save(creative);
    }

    @Test
    void findById_shouldReturnOptionalWithEntity() {
        // Arrange
        Long id = 42L;
        Creative creative = new Creative();
        when(repo.findById(id)).thenReturn(Optional.of(creative));

        // Act
        Optional<Creative> found = repo.findById(id);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(creative, found.get());
        verify(repo).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenNotFound() {
        // Arrange
        Long id = 999L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Creative> found = repo.findById(id);

        // Assert
        assertTrue(found.isEmpty());
        verify(repo).findById(id);
    }

    @Test
    void findAll_shouldReturnListOfEntities() {
        // Arrange
        Creative c1 = new Creative();
        Creative c2 = new Creative();
        List<Creative> mocked = Arrays.asList(c1, c2);

        when(repo.findAll()).thenReturn(mocked);

        // Act
        List<Creative> result = repo.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result).containsExactly(c1, c2); // if using AssertJ
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
        Creative creative = new Creative();
        when(repo.save(any(Creative.class))).thenReturn(creative);

        ArgumentCaptor<Creative> captor = ArgumentCaptor.forClass(Creative.class);

        // Act
        repo.save(creative);

        // Assert
        verify(repo).save(captor.capture());
        Creative passed = captor.getValue();
        assertNotNull(passed);
        // If you set fields above, assert them here.
        // assertEquals("Banner A", passed.getName());
    }
}
