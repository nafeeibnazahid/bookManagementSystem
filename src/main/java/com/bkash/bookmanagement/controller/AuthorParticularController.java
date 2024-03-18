package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.UpdateAuthorGenreRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.services.AuthorService;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
@RequestMapping("api/v1/author/{authorId}")
public class AuthorParticularController {
    private final AuthorService authorService;

    public AuthorParticularController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Optional<Author> getParticularAuthor(@PathVariable("authorId") Integer id) {
        return authorService.getSingleAuthor(id);
    }

    @PutMapping
    public void update(@PathVariable("authorId") Integer id, @RequestBody UpdateAuthorGenreRequest updateAuthorGenreRequest
                       ) {
        String name = updateAuthorGenreRequest.getName();
        Optional<Author> optAuth =  authorService.getSingleAuthor(id);
        if (optAuth.isEmpty()) {
            return;
        }
        Author author = optAuth.get();
        author.setName(name);
        authorService.addAuthor(author); // TODO : check whether it updating author
    }
}
