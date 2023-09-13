package com.exercise.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.exercise.Bookstore.domain.Book; 
import com.exercise.Bookstore.domain.BookRepository; 

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner bookDemo(BookRepository repository) { 
        return (args) -> {
            log.info("Save a couple of books");
            
            repository.save(new Book("Book Title 1", "Author 1", 2021, "ISBN-12345", 19.99));
            repository.save(new Book("Book Title 2", "Author 2", 2022, "ISBN-67890", 24.99));

            log.info("Fetch all books");
            for (Book book : repository.findAll()) {
                log.info(book.toString());
            }
        };
    }
}