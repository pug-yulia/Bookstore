package com.exercise.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.exercise.Bookstore.domain.Book; 
import com.exercise.Bookstore.domain.BookRepository;
import com.exercise.Bookstore.domain.Category;
import com.exercise.Bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner bookDemo(BookRepository srepository, CategoryRepository crepository) { 
        return (args) -> {
            log.info("Save a couple of books");
            
            crepository.save(new Category("Fantasy"));
			crepository.save(new Category("Business"));
			crepository.save(new Category("Biography"));
            
            srepository.save(new Book("Book Title 1", "Author 1", 2021, "ISBN-12345", 19.99, crepository.findByName("Fantasy").get(0)));
            srepository.save(new Book("Book Title 2", "Author 2", 2022, "ISBN-67890", 24.99, crepository.findByName("Business").get(0)));

            log.info("Fetch all books");
            for (Book book : srepository.findAll()) {
                log.info(book.toString());
            }
        };
    }
}