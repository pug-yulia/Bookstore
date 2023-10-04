package com.exercise.Bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.exercise.Bookstore.domain.Book;
import com.exercise.Bookstore.domain.BookRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void findByIsbnShouldReturnBook() {
		List<Book> books = bookRepository.findByIsbn("ISBN-12345");
		assertThat(books).hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Book Title 1");
	}

	@Test
	public void createNewBook() {
		Book book = new Book("New Book", "New Author", 2023, "ISBN-99999", 29.99, null);
		bookRepository.save(book);
		assertThat(book.getId()).isNotNull();
	}
}
