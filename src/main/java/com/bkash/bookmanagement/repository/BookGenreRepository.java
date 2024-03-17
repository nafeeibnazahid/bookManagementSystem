package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGenreRepository
    extends JpaRepository<BookGenre, Integer> {
}
