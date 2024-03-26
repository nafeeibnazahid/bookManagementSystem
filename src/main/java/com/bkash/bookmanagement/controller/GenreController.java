package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.common.Authority;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.services.GenreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@RestController
@RequestMapping("api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PreAuthorize(Authority.Plan.WRITE)
    @PostMapping
    public Genre addGenre(
            @Valid @RequestBody Genre genre
    ) {
        genre.setId(null);
        return genreService.addGenre(
                genre
        );
    }


    @PreAuthorize(Authority.Plan.WRITE)
    @PutMapping Genre updateGenre(
            @Valid @RequestBody Genre genre
    ) {
        if (genre.getId() == null) {
            throw new RuntimeException("genre id can't be null");
        }
        return genreService.updateGenre(genre);
    }


    @GetMapping
    public List<Genre> getGenres(
            @RequestParam Optional<Integer> id,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> bookId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return genreService.getGenre(id, name, bookId, offset, limit);
    }

    @PreAuthorize(Authority.Plan.WRITE)
    @DeleteMapping("/{genreId}")
    public void deleteGenre(@PathVariable("genreId") @NotNull Integer genreId) {
        genreService.deleteGenre(genreId);
    }

}
