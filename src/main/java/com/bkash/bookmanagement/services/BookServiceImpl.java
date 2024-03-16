package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.repository.BookRepository;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
