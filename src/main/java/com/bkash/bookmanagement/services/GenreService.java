package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GenreService {
    public Genre addGenre(Genre genre);

    public List<Genre> getGenre(
            Optional<Integer> id,
            Optional<String> name,
            Optional<Integer> bookId,
            Integer offset,
            Integer limit
    );

    public Genre updateGenre(Genre genre);

    public void deleteGenre(Integer genreId);
}
