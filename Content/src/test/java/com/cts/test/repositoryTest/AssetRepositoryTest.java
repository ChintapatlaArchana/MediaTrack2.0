package com.cts.test.repositoryTest;

import com.cts.model.Asset;
import com.cts.model.Asset.AssetType;
import com.cts.model.Category;
import com.cts.model.Title;
import com.cts.model.Title.ApplicationStatus;
import com.cts.repository.AssetRepository;
import com.cts.repository.CategoryRepository;
import com.cts.repository.TitleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AssetRepositoryTest {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Title createTitle() {
        Category category = new Category();
        category.setName("Movies");
        category.setDescription("All movie titles");
        Category savedCategory = categoryRepository.save(category);

        Title title = new Title();
        title.setName("Inception");
        title.setSynopsis("A mind-bending thriller");
        title.setGenre("Sci-Fi");
        title.setReleaseDate(LocalDate.of(2010, 7, 16));
        title.setRating("PG-13");
        title.setApplicationStatus(ApplicationStatus.available);
        title.setCategory(savedCategory);

        return titleRepository.save(title);
    }

    @Test
    @DisplayName("Save an asset and verify it is persisted")
    void testSaveAsset() {
        Title title = createTitle();

        Asset asset = new Asset();
        asset.setTitle(title);
        asset.setAssetType(AssetType.movie);
        asset.setDuration(148);
        asset.setLanguage("English");
        asset.setSubtitleLanguages(Arrays.asList("French", "Spanish"));
        asset.setAvailabilityStart(LocalDate.of(2024, 1, 1));
        asset.setAvailabilityEnd(LocalDate.of(2024, 12, 31));

        Asset savedAsset = assetRepository.save(asset);

        assertThat(savedAsset.getAssetId()).isNotNull();
        assertThat(savedAsset.getLanguage()).isEqualTo("English");
        assertThat(savedAsset.getSubtitleLanguages()).contains("French", "Spanish");
    }

    @Test
    @DisplayName("Find asset by ID")
    void testFindById() {
        Title title = createTitle();

        Asset asset = new Asset();
        asset.setTitle(title);
        asset.setAssetType(AssetType.clip);
        asset.setDuration(10);
        asset.setLanguage("English");
        asset.setSubtitleLanguages(List.of("German"));
        asset.setAvailabilityStart(LocalDate.of(2024, 5, 1));
        asset.setAvailabilityEnd(LocalDate.of(2024, 6, 1));

        Asset savedAsset = assetRepository.save(asset);

        Optional<Asset> found = assetRepository.findById(savedAsset.getAssetId());

        assertThat(found).isPresent();
        assertThat(found.get().getAssetType()).isEqualTo(AssetType.clip);
    }

    @Test
    @DisplayName("Find assets by Title ID")
    void testFindByTitleId() {
        Title title = createTitle();

        Asset a1 = new Asset();
        a1.setTitle(title);
        a1.setAssetType(AssetType.movie);
        a1.setDuration(120);
        a1.setLanguage("English");
        a1.setSubtitleLanguages(List.of("French"));
        a1.setAvailabilityStart(LocalDate.of(2024, 1, 1));
        a1.setAvailabilityEnd(LocalDate.of(2024, 12, 31));

        Asset a2 = new Asset();
        a2.setTitle(title);
        a2.setAssetType(AssetType.episode);
        a2.setDuration(45);
        a2.setLanguage("English");
        a2.setSubtitleLanguages(List.of("Spanish"));
        a2.setAvailabilityStart(LocalDate.of(2024, 2, 1));
        a2.setAvailabilityEnd(LocalDate.of(2024, 12, 31));

        assetRepository.save(a1);
        assetRepository.save(a2);

        List<Asset> assets = assetRepository.findByTitle_TitleId(title.getTitleId());

        assertThat(assets).hasSize(2);
        assertThat(assets).extracting(Asset::getAssetType)
                .containsExactlyInAnyOrder(AssetType.movie, AssetType.episode);
    }

    @Test
    @DisplayName("Delete asset")
    void testDeleteAsset() {
        Title title = createTitle();

        Asset asset = new Asset();
        asset.setTitle(title);
        asset.setAssetType(AssetType.movie);
        asset.setDuration(100);
        asset.setLanguage("English");
        asset.setSubtitleLanguages(List.of("Italian"));
        asset.setAvailabilityStart(LocalDate.of(2024, 3, 1));
        asset.setAvailabilityEnd(LocalDate.of(2024, 9, 1));

        Asset savedAsset = assetRepository.save(asset);

        assetRepository.delete(savedAsset);

        Optional<Asset> deleted = assetRepository.findById(savedAsset.getAssetId());
        assertThat(deleted).isEmpty();
    }
}
