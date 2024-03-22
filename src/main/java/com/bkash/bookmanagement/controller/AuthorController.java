package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.services.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("api/v1/author")
public class AuthorController {
    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void AddAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
    }

    @GetMapping
    public List<Author> getAuthors(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return authorService.getAuthors(offset, limit);
    }
}
