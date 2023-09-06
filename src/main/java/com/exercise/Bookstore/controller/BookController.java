package com.exercise.Bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/index")
    public String index() {
        return "index"; // This will map to an HTML template named "index.html"
    }
}