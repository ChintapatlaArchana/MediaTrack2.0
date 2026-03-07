package com.cts.repository;

import com.cts.model.Entitlement;
import com.cts.model.Entitlement.ContentScope;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EntitlementRepositoryTest {

    @Autowired
    private EntitlementRepository entitlementRepository;

    private Entitlement build(long userId, ContentScope scope, LocalDate granted, LocalDate expiry) {
        Entitlement e = new Entitlement();
        e.setUserId(userId);
        e.setContentScope(scope);
        e.setGrantedDate(granted);
        e.setExpiryDate(expiry);
        return e;
    }

    @Test
    void save_shouldGenerateId_andPersistAllFields() {
        Entitlement saved = entitlementRepository.save(
                build(999L, ContentScope.All, LocalDate.of(2026, 2, 1), LocalDate.of(2026, 3, 1)));

        assertThat(saved.getEntitlementId()).isPositive();
        assertThat(saved.getUserId()).isEqualTo(999L);
        assertThat(saved.getContentScope()).isEqualTo(ContentScope.All);
        assertThat(saved.getGrantedDate()).isEqualTo(LocalDate.of(2026, 2, 1));
        assertThat(saved.getExpiryDate()).isEqualTo(LocalDate.of(2026, 3, 1));
    }

    @Test
    void findById_shouldReturn_whenExists() {
        Entitlement saved = entitlementRepository.save(
                build(111L, ContentScope.Category, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31)));

        Optional<Entitlement> found = entitlementRepository.findById(saved.getEntitlementId());

        assertThat(found).isPresent();
        assertThat(found.get().getUserId()).isEqualTo(111L);
        assertThat(found.get().getContentScope()).isEqualTo(ContentScope.Category);
    }

    @Test
    void findAll_shouldReturnAllRows() {
        entitlementRepository.save(build(1L, ContentScope.All, null, null));
        entitlementRepository.save(build(2L, ContentScope.Title, LocalDate.now(), null));

        List<Entitlement> all = entitlementRepository.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting(Entitlement::getUserId)
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void update_shouldPersistChanges() {
        Entitlement saved = entitlementRepository.save(
                build(10L, ContentScope.All, LocalDate.of(2026, 2, 1), null));

        saved.setUserId(20L);
        saved.setContentScope(ContentScope.Title);
        saved.setExpiryDate(LocalDate.of(2026, 5, 31));

        Entitlement updated = entitlementRepository.save(saved);

        assertThat(updated.getEntitlementId()).isEqualTo(saved.getEntitlementId());
        assertThat(updated.getUserId()).isEqualTo(20L);
        assertThat(updated.getContentScope()).isEqualTo(ContentScope.Title);
        assertThat(updated.getExpiryDate()).isEqualTo(LocalDate.of(2026, 5, 31));
    }

    @Test
    void delete_shouldRemoveRow() {
        Entitlement saved = entitlementRepository.save(build(7L, ContentScope.All, null, null));
        long id = saved.getEntitlementId();

        entitlementRepository.delete(saved);

        assertThat(entitlementRepository.findById(id)).isNotPresent();
    }

    @Test
    void enumIsStoredAndRetrieved() {
        Entitlement saved = entitlementRepository.save(build(55L, ContentScope.Title, null, null));

        Entitlement found = entitlementRepository.findById(saved.getEntitlementId()).orElseThrow();

        assertThat(found.getContentScope()).isEqualTo(ContentScope.Title);
    }

    @Test
    void allowsNullDates_andNullEnum() {
        Entitlement saved = entitlementRepository.save(build(321L, null, null, null));

        assertThat(saved.getEntitlementId()).isPositive();
        assertThat(saved.getUserId()).isEqualTo(321L);
        assertThat(saved.getContentScope()).isNull();
        assertThat(saved.getGrantedDate()).isNull();
        assertThat(saved.getExpiryDate()).isNull();
    }
}