package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.BookGenre;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.repository.BookGenreRepository;
import com.bkash.bookmanagement.repository.BookRepository;
import com.bkash.bookmanagement.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
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

    private void valiedateForAddGenre(Genre genre) {
        Optional<Genre> prevGenre = genreRepository.findOne(Example.of(genre));
        if (prevGenre.isPresent()) {
            Integer prevId = prevGenre.get().getId();
            throw new RuntimeException("name already exist for genre with id " + prevId);
        }
    }

    @Override
    public Genre addGenre(Genre genre) {
        valiedateForAddGenre(genre);
        return genreRepository.save(genre);
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

    public Genre getSingleGenre(int genreId) {
        return getGenre(
                Optional.of(genreId),
                Optional.empty(),
                Optional.empty(),
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        ).get(0);
    }

    private void validateForUpdateGenre(
            Genre oldGenre,
            Genre newGenre) {
        if (oldGenre.equals(newGenre)) {
            throw new RuntimeException("same as previously saved genre");
        }
        Genre alreadyInDbWithSameName = genreRepository.findByName(newGenre.getName());
        if (alreadyInDbWithSameName != null && alreadyInDbWithSameName.getId() != newGenre.getId()) {
            throw new RuntimeException("same name in DB with id " + alreadyInDbWithSameName.getId());
        }
    }

    @Override
    public Genre updateGenre(Genre newGenre) {
        Genre oldGenre = genreRepository.getReferenceById(newGenre.getId());
        validateForUpdateGenre(oldGenre, newGenre);
        oldGenre.setName(newGenre.getName());
        return genreRepository.save(oldGenre);
    }


    @Override
    public void deleteGenre(Integer genreId){
        List<BookGenre> bookGenreList = bookGenreRepository.findBookGenreByGenreId(genreId) ;
        if (bookGenreList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append( "Genre id " + genreId + " exists for books with id ");
            for (var bookGenre : bookGenreList) {
                stringBuilder.append( bookGenre.getBookId() + ", " );
            }
            throw new RuntimeException(stringBuilder.toString());
        }
        if (genreRepository.findById(genreId).isEmpty()) {
            throw new RuntimeException("Genre is absent for id " + genreId);
        }
        genreRepository.deleteById(genreId);
    }
}
