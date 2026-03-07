package com.cts.test.repositoryTest;



import com.cts.model.Asset;
import com.cts.model.Asset.AssetType;
import com.cts.model.Category;
import com.cts.model.ContentVersion;
import com.cts.model.ContentVersion.VersionLabel;
import com.cts.model.Title;
import com.cts.model.Title.ApplicationStatus;
import com.cts.repository.AssetRepository;
import com.cts.repository.CategoryRepository;
import com.cts.repository.ContentVersionRepository;
import com.cts.repository.TitleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContentVersionRepositoryTest {

    @Autowired
    private ContentVersionRepository contentVersionRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Asset createAsset() {
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
        Title savedTitle = titleRepository.save(title);

        Asset asset = new Asset();
        asset.setTitle(savedTitle);
        asset.setAssetType(AssetType.movie);
        asset.setDuration(148);
        asset.setLanguage("English");
        asset.setSubtitleLanguages(List.of("French", "Spanish"));
        asset.setAvailabilityStart(LocalDate.of(2024, 1, 1));
        asset.setAvailabilityEnd(LocalDate.of(2024, 12, 31));

        return assetRepository.save(asset);
    }

    @Test
    @DisplayName("Save a content version and verify it is persisted")
    void testSaveContentVersion() {
        Asset asset = createAsset();

        ContentVersion version = new ContentVersion();
        version.setAsset(asset);
        version.setVersionLabel(VersionLabel.directorCut);
        version.setNotes("Director's extended cut");

        ContentVersion savedVersion = contentVersionRepository.save(version);

        assertThat(savedVersion.getVersionId()).isNotNull();
        assertThat(savedVersion.getVersionLabel()).isEqualTo(VersionLabel.directorCut);
    }

    @Test
    @DisplayName("Find content version by ID")
    void testFindById() {
        Asset asset = createAsset();

        ContentVersion version = new ContentVersion();
        version.setAsset(asset);
        version.setVersionLabel(VersionLabel.localized);
        version.setNotes("Localized for French audience");

        ContentVersion savedVersion = contentVersionRepository.save(version);

        Optional<ContentVersion> found = contentVersionRepository.findById(savedVersion.getVersionId());

        assertThat(found).isPresent();
        assertThat(found.get().getVersionLabel()).isEqualTo(VersionLabel.localized);
    }

    @Test
    @DisplayName("Find content versions by Asset ID")
    void testFindByAssetId() {
        Asset asset = createAsset();

        ContentVersion v1 = new ContentVersion();
        v1.setAsset(asset);
        v1.setVersionLabel(VersionLabel.directorCut);
        v1.setNotes("Extended cut");

        ContentVersion v2 = new ContentVersion();
        v2.setAsset(asset);
        v2.setVersionLabel(VersionLabel.edited);
        v2.setNotes("Edited for TV");

        contentVersionRepository.save(v1);
        contentVersionRepository.save(v2);

        List<ContentVersion> versions = contentVersionRepository.findByAsset_AssetId(asset.getAssetId());

        assertThat(versions).hasSize(2);
        assertThat(versions).extracting(ContentVersion::getVersionLabel)
                .containsExactlyInAnyOrder(VersionLabel.directorCut, VersionLabel.edited);
    }

    @Test
    @DisplayName("Delete content version")
    void testDeleteContentVersion() {
        Asset asset = createAsset();

        ContentVersion version = new ContentVersion();
        version.setAsset(asset);
        version.setVersionLabel(VersionLabel.edited);
        version.setNotes("Edited for TV broadcast");

        ContentVersion savedVersion = contentVersionRepository.save(version);

        contentVersionRepository.delete(savedVersion);

        Optional<ContentVersion> deleted = contentVersionRepository.findById(savedVersion.getVersionId());
        assertThat(deleted).isEmpty();
    }
}

