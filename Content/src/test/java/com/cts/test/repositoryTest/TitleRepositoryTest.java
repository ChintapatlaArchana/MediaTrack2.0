package com.cts.test.repositoryTest;



import com.cts.model.Category;
import com.cts.model.Title;
import com.cts.model.Title.ApplicationStatus;
import com.cts.repository.CategoryRepository;
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
class TitleRepositoryTest {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category createCategory() {
        Category category = new Category();
        category.setName("Movies");
        category.setDescription("All movie titles");
        return categoryRepository.save(category);
    }

    @Test
    @DisplayName("Save a title and verify it is persisted")
    void testSaveTitle() {
        Category category = createCategory();

        Title title = new Title();
        title.setName("Inception");
        title.setSynopsis("A mind-bending thriller");
        title.setGenre("Sci-Fi");
        title.setReleaseDate(LocalDate.of(2010, 7, 16));
        title.setRating("PG-13");
        title.setApplicationStatus(ApplicationStatus.available);
        title.setCategory(category);

        Title savedTitle = titleRepository.save(title);

        assertThat(savedTitle.getTitleId()).isNotNull();
        assertThat(savedTitle.getName()).isEqualTo("Inception");
        assertThat(savedTitle.getCategory().getName()).isEqualTo("Movies");
    }

    @Test
    @DisplayName("Find title by ID")
    void testFindById() {
        Category category = createCategory();

        Title title = new Title();
        title.setName("Interstellar");
        title.setSynopsis("Exploration of space and time");
        title.setGenre("Sci-Fi");
        title.setReleaseDate(LocalDate.of(2014, 11, 7));
        title.setRating("PG-13");
        title.setApplicationStatus(ApplicationStatus.available);
        title.setCategory(category);

        Title savedTitle = titleRepository.save(title);

        Optional<Title> found = titleRepository.findById(savedTitle.getTitleId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Interstellar");
    }

    @Test
    @DisplayName("Find all titles")
    void testFindAll() {
        Category category = createCategory();

        Title t1 = new Title();
        t1.setName("The Dark Knight");
        t1.setSynopsis("Batman vs Joker");
        t1.setGenre("Action");
        t1.setReleaseDate(LocalDate.of(2008, 7, 18));
        t1.setRating("PG-13");
        t1.setApplicationStatus(ApplicationStatus.available);
        t1.setCategory(category);

        Title t2 = new Title();
        t2.setName("Tenet");
        t2.setSynopsis("Time inversion thriller");
        t2.setGenre("Sci-Fi");
        t2.setReleaseDate(LocalDate.of(2020, 8, 26));
        t2.setRating("PG-13");
        t2.setApplicationStatus(ApplicationStatus.available);
        t2.setCategory(category);

        titleRepository.save(t1);
        titleRepository.save(t2);

        List<Title> titles = titleRepository.findAll();

        assertThat(titles).hasSize(2);
        assertThat(titles).extracting(Title::getName)
                .containsExactlyInAnyOrder("The Dark Knight", "Tenet");
    }

    @Test
    @DisplayName("Delete title")
    void testDeleteTitle() {
        Category category = createCategory();

        Title title = new Title();
        title.setName("Memento");
        title.setSynopsis("A man with short-term memory loss");
        title.setGenre("Thriller");
        title.setReleaseDate(LocalDate.of(2000, 9, 5));
        title.setRating("R");
        title.setApplicationStatus(ApplicationStatus.expired);
        title.setCategory(category);

        Title savedTitle = titleRepository.save(title);

        titleRepository.delete(savedTitle);

        Optional<Title> deleted = titleRepository.findById(savedTitle.getTitleId());
        assertThat(deleted).isEmpty();
    }
}
