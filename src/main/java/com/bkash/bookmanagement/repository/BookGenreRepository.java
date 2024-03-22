package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookGenreRepository
        extends JpaRepository<BookGenre, Integer> {

    List<BookGenre> findBookGenreByGenreId(Integer genreId);

    List<BookGenre> findBookGenreByBookId(Integer bookId);
}
