package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GenreService {
    public void addGenre(Genre genre);

    public List<Genre> getGenre(
            Optional<Integer> id,
            Optional<String> name,
            Integer offset,
            Integer limit
    );


}
