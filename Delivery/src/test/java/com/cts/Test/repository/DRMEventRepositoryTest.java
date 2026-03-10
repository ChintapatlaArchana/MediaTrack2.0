package com.cts.Test.repository;

import com.cts.model.DRMEvent;
import com.cts.model.DRMEvent.LicenseStatus;
import com.cts.model.PlaybackSession;
import com.cts.repository.DRMEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DRMEventRepositoryTest {

    @Autowired
    private DRMEventRepository repository;

    @Autowired
    private EntityManager em;

    private PlaybackSession newSession(Long userId) {
        PlaybackSession s = new PlaybackSession();
        s.setUserId(userId);
        em.persist(s); // ensure it's managed and has ID
        return s;
    }

    private DRMEvent newEvent(PlaybackSession s, String drmType, LocalDateTime time, LicenseStatus status) {
        DRMEvent e = new DRMEvent();
        e.setPlaybackSession(s);
        e.setDrmType(drmType);
        e.setEventTime(time);
        e.setLicenseStatus(status);
        return e;
    }

    @Test
    @DisplayName("save() should persist DRMEvent and generate drmEventID")
    void save_shouldPersistAndGenerateId() {
        PlaybackSession s = newSession(1001L);
        DRMEvent toSave = newEvent(s, "Widevine", LocalDateTime.now(), LicenseStatus.Granted);

        DRMEvent saved = repository.save(toSave);

        assertNotNull(saved.getDrmEventID(), "drmEventID should be generated");
        assertEquals("Widevine", saved.getDrmType());
        assertEquals(LicenseStatus.Granted, saved.getLicenseStatus());
        assertNotNull(saved.getPlaybackSession());
        assertNotNull(saved.getPlaybackSession().getSessionId());
    }

    @Test
    @DisplayName("findById() should return entity when present")
    void findById_whenPresent() {
        PlaybackSession s = newSession(2001L);
        DRMEvent saved = repository.save(newEvent(s, "PlayReady", LocalDateTime.now().minusMinutes(10), LicenseStatus.Denied));

        Optional<DRMEvent> found = repository.findById(saved.getDrmEventID());

        assertTrue(found.isPresent());
        assertEquals("PlayReady", found.get().getDrmType());
        assertEquals(LicenseStatus.Denied, found.get().getLicenseStatus());
    }

    @Test
    @DisplayName("findById() should return empty when not present")
    void findById_whenNotPresent() {
        Optional<DRMEvent> found = repository.findById(999_999L);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("findByPlaybackSession_SessionId() should return events of a given session")
    void findByPlaybackSessionSessionId_returnsEvents() {
        PlaybackSession s1 = newSession(3001L);
        PlaybackSession s2 = newSession(3002L);

        DRMEvent e1 = repository.save(newEvent(s1, "Widevine", LocalDateTime.now().minusMinutes(20), LicenseStatus.Granted));
        DRMEvent e2 = repository.save(newEvent(s1, "Widevine", LocalDateTime.now().minusMinutes(10), LicenseStatus.Denied));
        DRMEvent e3 = repository.save(newEvent(s2, "PlayReady", LocalDateTime.now().minusMinutes(5), LicenseStatus.Expired));

        List<DRMEvent> session1Events = repository.findByPlaybackSession_SessionId(s1.getSessionId());

        assertEquals(2, session1Events.size());
        assertTrue(session1Events.stream().anyMatch(e -> e.getDrmEventID().equals(e1.getDrmEventID())));
        assertTrue(session1Events.stream().anyMatch(e -> e.getDrmEventID().equals(e2.getDrmEventID())));
        assertTrue(session1Events.stream().noneMatch(e -> e.getDrmEventID().equals(e3.getDrmEventID())));
    }

    @Test
    @DisplayName("findByPlaybackSession_SessionId() should return empty for unknown session")
    void findByPlaybackSessionSessionId_emptyWhenUnknown() {
        List<DRMEvent> result = repository.findByPlaybackSession_SessionId(123456789L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("save() twice should update existing DRMEvent")
    void update_shouldPersistChanges() {
        PlaybackSession s = newSession(4001L);
        DRMEvent saved = repository.save(newEvent(s, "Widevine", LocalDateTime.now().minusMinutes(15), LicenseStatus.Denied));

        saved.setDrmType("PlayReady");
        saved.setLicenseStatus(LicenseStatus.Granted);
        saved.setEventTime(LocalDateTime.now());

        DRMEvent updated = repository.save(saved);

        assertEquals(saved.getDrmEventID(), updated.getDrmEventID());
        assertEquals("PlayReady", updated.getDrmType());
        assertEquals(LicenseStatus.Granted, updated.getLicenseStatus());
        assertNotNull(updated.getEventTime());
    }

    @Test
    @DisplayName("delete() should remove the DRMEvent")
    void delete_shouldRemove() {
        PlaybackSession s = newSession(5001L);
        DRMEvent saved = repository.save(newEvent(s, "Widevine", LocalDateTime.now(), LicenseStatus.Expired));

        repository.delete(saved);

        assertTrue(repository.findById(saved.getDrmEventID()).isEmpty());
    }

    @Test
    @DisplayName("Enum should be stored and retrieved as STRING")
    void enum_shouldBeStoredAsString() {
        PlaybackSession s = newSession(6001L);
        DRMEvent saved = repository.save(newEvent(s, "PlayReady", LocalDateTime.now(), LicenseStatus.Expired));

        DRMEvent reloaded = repository.findById(saved.getDrmEventID()).orElseThrow();
        assertEquals(LicenseStatus.Expired, reloaded.getLicenseStatus());
    }
}