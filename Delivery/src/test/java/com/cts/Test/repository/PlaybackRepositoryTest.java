package com.cts.Test.repository;

import com.cts.model.PlaybackSession;
import com.cts.model.PlaybackSession.Status;
import com.cts.repository.PlaybackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PlaybackRepositoryTest {

    @Autowired
    private PlaybackRepository repository;

    private PlaybackSession buildSession(Long userId, Long assetId, Status status) {
        PlaybackSession s = new PlaybackSession();
        s.setUserId(userId);
        s.setAssetId(assetId);
        s.setStartTime(LocalDateTime.now().minusMinutes(10));
        s.setEndTime(LocalDateTime.now());
        s.setBitrateAvg(4500.5);
        s.setBufferEvents(2);
        s.setStatus(status);
        return s;
    }

    @Test
    @DisplayName("save() should persist session and generate sessionId")
    void save_shouldPersistAndGenerateId() {
        PlaybackSession saved = repository.save(buildSession(100L, 10L, Status.ACTIVE));

        assertNotNull(saved.getSessionId());
        assertEquals(100L, saved.getUserId());
        assertEquals(Status.ACTIVE, saved.getStatus());
    }

    @Test
    @DisplayName("findById should return session when present")
    void findById_whenPresent() {
        PlaybackSession saved = repository.save(buildSession(200L, 20L, Status.COMPLETED));

        Optional<PlaybackSession> found = repository.findById(saved.getSessionId());

        assertTrue(found.isPresent());
        assertEquals(Status.COMPLETED, found.get().getStatus());
    }

    @Test
    @DisplayName("findById should return empty when not present")
    void findById_whenNotPresent() {
        assertTrue(repository.findById(999L).isEmpty());
    }

    @Test
    @DisplayName("findAll should return all stored sessions")
    void findAll_shouldReturnAll() {
        repository.save(buildSession(300L, 30L, Status.ACTIVE));
        repository.save(buildSession(400L, 40L, Status.ABORTED));

        List<PlaybackSession> sessions = repository.findAll();

        assertEquals(2, sessions.size());
    }

    @Test
    @DisplayName("update should modify existing session")
    void update_shouldModifySession() {
        PlaybackSession saved = repository.save(buildSession(500L, 50L, Status.ACTIVE));

        saved.setStatus(Status.COMPLETED);
        saved.setBitrateAvg(5500.0);

        PlaybackSession updated = repository.save(saved);

        assertEquals(Status.COMPLETED, updated.getStatus());
        assertEquals(5500.0, updated.getBitrateAvg());
    }

    @Test
    @DisplayName("delete should remove session")
    void delete_shouldRemove() {
        PlaybackSession saved = repository.save(buildSession(600L, 60L, Status.ABORTED));

        repository.delete(saved);

        assertTrue(repository.findById(saved.getSessionId()).isEmpty());
    }
}
