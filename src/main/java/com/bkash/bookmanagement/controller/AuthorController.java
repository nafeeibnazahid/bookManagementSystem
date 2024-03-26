package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.common.Authority;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.services.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("api/v1/author")
public class AuthorController {
    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize(Authority.Plan.WRITE)
    @PostMapping
    public Author AddAuthor(
            @Valid @RequestBody Author author
    ) {
        author.setId(null);
        return authorService.addAuthor(author);
    }


    @PreAuthorize(Authority.Plan.WRITE)
    @PutMapping
    public Author updateAuthor(
            @Valid @RequestBody Author author
    ) {
        if (author.getId() == null) {
            throw new RuntimeException("author id can't be null");
        }
        return authorService.updateAuthor(author);
    }

    @GetMapping
    public List<Author> getAuthors(
            @RequestParam Optional<Integer> id,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> bookId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return authorService.getAuthors(
                id,
                name,
                bookId,
                offset,
                limit
        );
    }

    @PreAuthorize(Authority.Plan.WRITE)
    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable("authorId") @NotNull Integer authorId) {
        authorService.deleteAuthor(authorId);
    }


}
