package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.repository.BookGenreRepository;
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

    public GenreServiceImple(
            GenreRepository genreRepository,
            BookGenreRepository bookGenreRepository
    ) {
        this.genreRepository = genreRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public void addGenre(Genre genre) {
        try {
            genreRepository.save(genre);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public List<Genre> getGenre(
            Optional<Integer> id,
            Optional<String> name,
            Integer offset,
            Integer limit
    ) {
        int pageNum = offset / limit;
        return genreRepository.getGenre(
                id,
                name,
                offset,
                limit
        );
//        return genreRepository.findByNameId(id, name, offset, limit);
//        return genreRepository.findAllByOrderByIdDesc(PageRequest.of(pageNum, limit));
    }


}
