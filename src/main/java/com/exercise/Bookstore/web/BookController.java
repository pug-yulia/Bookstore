package com.exercise.Bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exercise.Bookstore.domain.Book;
import com.exercise.Bookstore.domain.BookRepository;
import com.exercise.Bookstore.domain.CategoryRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository crepository;

	@RequestMapping(value = { "/", "/booklist" })
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());

        // Get the authenticated username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "booklist";
    }
	
	// RESTful service to get all books
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> studentListRest() {	
        return (List<Book>) repository.findAll();
    }    

	// RESTful service to get book by id
    @RequestMapping(value="/book/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findStudentRest(@PathVariable("id") Long bookId) {	
    	return repository.findById(bookId);
    }       
    
	
	@RequestMapping(value = "/add")
	public String addBook(Model model){
	    model.addAttribute("book", new Book());
	    model.addAttribute("categories", crepository.findAll());
	    return "addbook";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute("book") Book book){
	    repository.save(book);
	    return "redirect:booklist";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long bookId, Model model) {
	    repository.deleteById(bookId);
	    return "redirect:../booklist";
	}
	
	@RequestMapping("/edit/{id}")
	public String editBook(@PathVariable("id") Long bookId, Model model) {
	    
	    Book book = repository.findById(bookId).orElse(null);
	    if (book != null) {
	        model.addAttribute("book", book);
	        model.addAttribute("categories", crepository.findAll());
	        return "editbook"; 
	    } else {
	        return "redirect:/booklist";
	    }
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateBook(@ModelAttribute("book") Book book) {
	    repository.save(book);
	    return "redirect:/booklist";
	}

}