package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;

import java.util.List;

public interface BookService {
    public List<Book> getBooks();

    public String addBook(Book book, List<Integer> auhtorIdList, List<Integer> genreIdList);
}
