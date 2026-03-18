package com.cts.test.repositoryTest;

import com.cts.model.Campaign;
import com.cts.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // optional: AssertJ for fluent assertions
import static org.junit.jupiter.api.Assertions.*;          // JUnit assertions
import static org.mockito.Mockito.*;

class CampaignRepositoryTest {

    // Mock the repository interface
    private final CampaignRepository repo = mock(CampaignRepository.class);

    @Test
    void save_shouldReturnSavedEntity() {
        // Arrange
        Campaign campaign = new Campaign();
        // If your entity has mandatory fields, set them here:
        // campaign.setName("Summer Blast");
        // campaign.setAdvertiser("Nike");

        when(repo.save(campaign)).thenReturn(campaign);

        // Act
        Campaign saved = repo.save(campaign);

        // Assert
        assertNotNull(saved);
        assertEquals(campaign, saved);
        verify(repo, times(1)).save(campaign);
    }

    @Test
    void findById_shouldReturnOptionalWithEntity() {
        // Arrange
        Long id = 1L;
        Campaign campaign = new Campaign();
        when(repo.findById(id)).thenReturn(Optional.of(campaign));

        // Act
        Optional<Campaign> found = repo.findById(id);

        // Assert
        assertTrue(found.isPresent());
        assertEquals(campaign, found.get());
        verify(repo).findById(id);
    }

    @Test
    void findById_shouldReturnEmptyOptionalWhenNotFound() {
        // Arrange
        Long id = 404L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Campaign> found = repo.findById(id);

        // Assert
        assertTrue(found.isEmpty());
        verify(repo).findById(id);
    }

    @Test
    void findAll_shouldReturnListOfEntities() {
        // Arrange
        Campaign c1 = new Campaign();
        Campaign c2 = new Campaign();
        List<Campaign> mocked = Arrays.asList(c1, c2);

        when(repo.findAll()).thenReturn(mocked);

        // Act
        List<Campaign> result = repo.findAll();

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
        Campaign campaign = new Campaign();
        when(repo.save(any(Campaign.class))).thenReturn(campaign);

        ArgumentCaptor<Campaign> captor = ArgumentCaptor.forClass(Campaign.class);

        // Act
        repo.save(campaign);

        // Assert
        verify(repo).save(captor.capture());
        Campaign passed = captor.getValue();
        assertNotNull(passed);
        // If you set fields above, assert them here:
        // assertEquals("Summer Blast", passed.getName());
    }
}