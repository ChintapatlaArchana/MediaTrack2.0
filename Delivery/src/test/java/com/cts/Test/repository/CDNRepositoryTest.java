package com.cts.Test.repository;

import com.cts.model.CDNEndpoint;
import com.cts.model.CDNEndpoint.Status;
import com.cts.repository.CDNRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CDNRepositoryTest {

    @Autowired
    private CDNRepository repository;

    private CDNEndpoint build(String name, String url, String region, Status status) {
        CDNEndpoint e = new CDNEndpoint();
        e.setName(name);
        e.setBaseURL(url);
        e.setRegion(region);
        e.setStatus(status);
        return e;
    }

    @Test
    @DisplayName("save() should persist entity and generate ID")
    void save_shouldPersistAndGenerateId() {
        CDNEndpoint toSave = build("Akamai", "https://cdn.example.com", "APAC", Status.Active);

        CDNEndpoint saved = repository.save(toSave);

        assertNotNull(saved.getEndpointID(), "ID should be generated");
        assertEquals("Akamai", saved.getName());
        assertEquals("APAC", saved.getRegion());
        assertEquals(Status.Active, saved.getStatus());
    }

    @Test
    @DisplayName("findById() should return entity when it exists")
    void findById_whenExisting() {
        CDNEndpoint saved = repository.save(build("CloudFront", "https://cf.example.com", "US", Status.Active));

        Optional<CDNEndpoint> found = repository.findById(saved.getEndpointID());

        assertTrue(found.isPresent());
        assertEquals("CloudFront", found.get().getName());
    }

    @Test
    @DisplayName("findById() should return empty when entity does not exist")
    void findById_whenNonExisting() {
        Optional<CDNEndpoint> found = repository.findById(999_999L);

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("findAll() should return all persisted entities")
    void findAll_returnsAll() {
        repository.save(build("Cloudflare", "https://cl.example.com", "EU", Status.Active));
        repository.save(build("Fastly", "https://fs.example.com", "US", Status.Inactive));

        List<CDNEndpoint> all = repository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("save() should update existing entity on second save")
    void update_shouldPersistChanges() {
        CDNEndpoint saved = repository.save(build("Edgio", "https://edgio.example.com", "APAC", Status.Inactive));

        saved.setRegion("EU");
        saved.setStatus(Status.Active);
        CDNEndpoint updated = repository.save(saved);

        assertEquals(saved.getEndpointID(), updated.getEndpointID());
        assertEquals("EU", updated.getRegion());
        assertEquals(Status.Active, updated.getStatus());
    }

    @Test
    @DisplayName("delete() should remove the entity")
    void delete_shouldRemove() {
        CDNEndpoint saved = repository.save(build("Lumen", "https://lumen.example.com", "US", Status.Active));

        repository.delete(saved);

        assertTrue(repository.findById(saved.getEndpointID()).isEmpty());
    }

    @Test
    @DisplayName("Enum should be stored and retrieved as STRING")
    void enum_shouldBeStoredAsString() {
        CDNEndpoint saved = repository.save(build("Akamai-Enum", "https://enum.example.com", "APAC", Status.Inactive));

        CDNEndpoint reloaded = repository.findById(saved.getEndpointID()).orElseThrow();
        assertEquals(Status.Inactive, reloaded.getStatus());
    }
}