package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.repository.BookGenreRepository;
import com.bkash.bookmanagement.repository.BookRepository;
import com.bkash.bookmanagement.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreServiceImple implements GenreService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GenreRepository genreRepository;
    private final BookGenreRepository bookGenreRepository;

    private final BookRepository bookRepository;

    public GenreServiceImple(
            GenreRepository genreRepository,
            BookGenreRepository bookGenreRepository,
            BookRepository bookRepository
    ) {
        this.genreRepository = genreRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getGenre(
            Optional<Integer> id,
            Optional<String> name,
            Optional<Integer> bookId,
            Integer offset,
            Integer limit
    ) {
        List<Genre> genreLIst = genreRepository.getGenre(
                id,
                name,
                bookId,
                offset,
                limit
        );
        for (Genre genre : genreLIst) {
            genre.setBookList(bookRepository.getBooks(
                    null,
                    null,
                    null,
                    null,
                    null,
                    Optional.of(genre.getId()),
                    Constant.OFFSET_ZERO,
                    Constant.INFINITE_LIMIT
            ));
        }
        return genreLIst;
    }


}
