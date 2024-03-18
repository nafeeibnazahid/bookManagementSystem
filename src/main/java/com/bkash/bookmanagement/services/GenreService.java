package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GenreService {
    public void addGenre(Genre genre);

    public List<Author> getAuthors();
}
