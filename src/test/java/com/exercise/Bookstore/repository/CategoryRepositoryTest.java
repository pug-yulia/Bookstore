package com.exercise.Bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exercise.Bookstore.domain.Category;
import com.exercise.Bookstore.domain.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void findByNameShouldReturnCategory() {
		List<Category> categories = categoryRepository.findByName("Fantasy");
		assertThat(categories).hasSize(1);
		assertThat(categories.get(0).getName()).isEqualTo("Fantasy");
	}

	@Test
	public void createNewCategory() {
		Category category = new Category("New Category");
		categoryRepository.save(category);
		assertThat(category.getCategoryId()).isNotNull();
	}
}
