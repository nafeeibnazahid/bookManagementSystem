package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.UpdateAuthorGenreRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.services.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
@RequestMapping("api/v1/author/{authorId}")
public class ParticularAuthorController {
    private final AuthorService authorService;

    public ParticularAuthorController(AuthorService authorService) {
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
        authorService.addAuthor(author);
    }

    @DeleteMapping
    public void delete(@PathVariable("authorId") Integer id) {
        authorService.deleteById(id);
    }
}
