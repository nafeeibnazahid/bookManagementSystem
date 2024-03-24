package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.BookGenre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookGenreRepository
        extends JpaRepository<BookGenre, Integer> {

    List<BookGenre> findBookGenreByGenreId(Integer genreId);

    List<BookGenre> findBookGenreByBookId(Integer bookId);

    public boolean existsByBookIdAndGenreId(int bookId, int genreId);

    @Transactional
    public List<BookGenre> deleteByBookId(int bookId);
}
