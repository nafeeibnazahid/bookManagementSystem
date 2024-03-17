package com.bkash.bookmanagement.repository;

import com.bkash.bookmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository
    extends JpaRepository<Book, Integer> {

}

