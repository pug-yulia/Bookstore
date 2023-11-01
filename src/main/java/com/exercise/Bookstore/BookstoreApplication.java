package com.exercise.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exercise.Bookstore.domain.Book;
import com.exercise.Bookstore.domain.BookRepository;
import com.exercise.Bookstore.domain.Category;
import com.exercise.Bookstore.domain.CategoryRepository;
import com.exercise.Bookstore.domain.*;
import com.exercise.Bookstore.domain.AppUserRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AppUserRepository userRepository;

	@Bean
	public CommandLineRunner bookDemo(BookRepository srepository, CategoryRepository crepository) {
		return (args) -> {
			log.info("Save a couple of books");

			crepository.save(new Category("Fantasy"));
			crepository.save(new Category("Business"));
			crepository.save(new Category("Biography"));

			srepository.save(new Book("Book Title 1", "Author 1", 2021, "ISBN-12345", 19.99,
					crepository.findByName("Fantasy").get(0)));
			srepository.save(new Book("Book Title 2", "Author 2", 2022, "ISBN-67890", 24.99,
					crepository.findByName("Business").get(0)));

			log.info("Fetch all books");
			for (Book book : srepository.findAll()) {
				log.info(book.toString());
			}

			// Create admin and user users
			AppUser admin = new AppUser();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin"));
			admin.setEmail("admin@example.com");
			admin.setRole("ROLE_ADMIN");

			AppUser user = new AppUser();
			user.setUsername("user");
			user.setPassword(passwordEncoder.encode("user"));
			user.setEmail("user@example.com");
			user.setRole("ROLE_USER");

			// Save users with exception handling
			try {
				userRepository.save(admin);
				log.info("Admin user created.");
			} catch (DataIntegrityViolationException e) {
				log.error("Admin user creation failed due to a constraint violation.", e);
			}

			try {
				userRepository.save(user);
				log.info("Regular user created.");
			} catch (DataIntegrityViolationException e) {
				log.error("User creation failed due to a constraint violation.", e);
			}
		};
	}

}