package com.cts.test.repositoryTest;

import com.cts.model.Category;
import com.cts.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Save a category and verify it is persisted")
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Technology");
        category.setDescription("All tech related topics");

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getCategoryId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Technology");
    }

    @Test
    @DisplayName("Find category by ID")
    void testFindById() {
        Category category = new Category();
        category.setName("Science");
        category.setDescription("Scientific topics");
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> found = categoryRepository.findById(savedCategory.getCategoryId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Science");
    }

    @Test
    @DisplayName("Find all categories")
    void testFindAll() {
        Category cat1 = new Category();
        cat1.setName("Sports");
        cat1.setDescription("All sports related topics");

        Category cat2 = new Category();
        cat2.setName("Health");
        cat2.setDescription("Health and wellness");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).hasSize(2);
        assertThat(categories).extracting(Category::getName)
                .containsExactlyInAnyOrder("Sports", "Health");
    }

    @Test
    @DisplayName("Delete category")
    void testDeleteCategory() {
        Category category = new Category();
        category.setName("Travel");
        category.setDescription("Travel guides and tips");
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.delete(savedCategory);

        Optional<Category> deleted = categoryRepository.findById(savedCategory.getCategoryId());
        assertThat(deleted).isEmpty();
    }
}
