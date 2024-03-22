package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.dto.GetBooksRequest;
import com.bkash.bookmanagement.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBooks();

    public String addBook(
            Book book,
            List<Integer> auhtorIdList,
            List<Integer> genreIdList
    );

    public List<Book> findByNameLike(
            String likePattern,
            Integer offset,
            Integer limit
    );

    public List<Book> getBookByGetBookReq(GetBooksRequest getBooksRequest);
}
