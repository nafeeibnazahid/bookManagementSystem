package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository
    extends JpaRepository<Book, Integer> {
}

