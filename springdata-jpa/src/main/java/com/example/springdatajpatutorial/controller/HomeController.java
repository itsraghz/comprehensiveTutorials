package com.example.springdatajpatutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling basic view navigation for the Spring Data JPA Tutorial
 */
@Controller
public class HomeController {

    /**
     * Serves the index page at the root URL
     * 
     * @return the name of the template to render (index.html)
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

