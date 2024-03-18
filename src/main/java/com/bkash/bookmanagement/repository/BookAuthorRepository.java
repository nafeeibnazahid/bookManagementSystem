package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAuthorRepository
    extends JpaRepository<BookAuthor, Integer> {

    List<BookAuthor> findBookAuthorByAuthorId(Integer authorId);

    List<BookAuthor> findBookAuthorByBookId(Integer bookId);

}
