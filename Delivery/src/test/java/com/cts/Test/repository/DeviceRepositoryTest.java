package com.cts.Test.repository;

import com.cts.model.Device;
import com.cts.model.Device.DeviceType;
import com.cts.model.Device.Status;
import com.cts.repository.DeviceRepository;
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
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository repository;

    private Device build(Long userId, LocalDateTime registered, DeviceType type, Status status) {
        Device d = new Device();
        d.setUserId(userId);
        d.setRegisteredDate(registered);
        d.setDeviceType(type);
        d.setStatus(status);
        return d;
    }

    @Test
    @DisplayName("save() should persist and generate deviceId")
    void save_shouldPersistAndGenerateId() {
        Device toSave = build(101L, LocalDateTime.now(), DeviceType.Mobile, Status.Active);

        Device saved = repository.save(toSave);

        assertNotNull(saved.getDeviceId(), "deviceId should be generated");
        assertEquals(101L, saved.getUserId());
        assertEquals(DeviceType.Mobile, saved.getDeviceType());
        assertEquals(Status.Active, saved.getStatus());
        assertNotNull(saved.getRegisteredDate());
    }

    @Test
    @DisplayName("findById() should return entity when present")
    void findById_whenPresent() {
        Device saved = repository.save(build(102L, LocalDateTime.now().minusDays(1), DeviceType.Web, Status.Revoked));

        Optional<Device> found = repository.findById(saved.getDeviceId());

        assertTrue(found.isPresent());
        assertEquals(102L, found.get().getUserId());
        assertEquals(Status.Revoked, found.get().getStatus());
    }

    @Test
    @DisplayName("findById() should return empty when not present")
    void findById_whenNotPresent() {
        Optional<Device> found = repository.findById(999_999L);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("findAll() should return all persisted devices")
    void findAll_returnsAll() {
        repository.save(build(201L, LocalDateTime.now().minusHours(2), DeviceType.Tv, Status.Active));
        repository.save(build(202L, LocalDateTime.now().minusHours(3), DeviceType.Web, Status.Revoked));

        List<Device> all = repository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("save() twice should update existing device")
    void update_shouldPersistChanges() {
        Device saved = repository.save(build(300L, LocalDateTime.now(), DeviceType.Mobile, Status.Active));

        saved.setStatus(Status.Revoked);
        saved.setDeviceType(DeviceType.Web);
        Device updated = repository.save(saved);

        assertEquals(saved.getDeviceId(), updated.getDeviceId());
        assertEquals(Status.Revoked, updated.getStatus());
        assertEquals(DeviceType.Web, updated.getDeviceType());
    }

    @Test
    @DisplayName("delete() should remove the device")
    void delete_shouldRemove() {
        Device saved = repository.save(build(400L, LocalDateTime.now(), DeviceType.Tv, Status.Active));

        repository.delete(saved);

        assertTrue(repository.findById(saved.getDeviceId()).isEmpty());
    }

    @Test
    @DisplayName("Enum fields should be stored and retrieved as STRING")
    void enums_shouldRoundTripAsString() {
        Device saved = repository.save(build(500L, LocalDateTime.now(), DeviceType.Web, Status.Revoked));

        Device reloaded = repository.findById(saved.getDeviceId()).orElseThrow();
        assertEquals(DeviceType.Web, reloaded.getDeviceType());
        assertEquals(Status.Revoked, reloaded.getStatus());
    }
}