package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.services.GenreService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public void addGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);
    }

    @GetMapping
    public List<Genre> getGenres(@RequestParam(defaultValue = "0") int offset,
                                 @RequestParam(defaultValue = "10") int limit) {
        return genreService.getGenre(offset, limit);
    }

}
