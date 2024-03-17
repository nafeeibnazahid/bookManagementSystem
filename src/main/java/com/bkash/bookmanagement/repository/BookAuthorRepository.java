package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository
    extends JpaRepository<BookAuthor, Integer> {

}
