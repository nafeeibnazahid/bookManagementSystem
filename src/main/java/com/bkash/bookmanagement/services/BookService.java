package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.dto.GetBooksRequest;
import com.bkash.bookmanagement.entity.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<Book> getBooks(
            Optional<Integer> id,
            Optional<Date> startDate,
            Optional<Date> endDate,
            Optional<String> partialName,
            Integer offset,
            Integer limit
    );


        public String addBook(
            Book book,
            List<Integer> auhtorIdList,
            List<Integer> genreIdList
    );

    public void saveOnlyBook(Book book);

    public void bookAuthorGenreSave(
            Book book,
            List<Integer> authorIdList,
            List<Integer> genreIdList
    );


        public List<Book> findByNameLike(
            String likePattern,
            Integer offset,
            Integer limit
    );

    public List<Book> getBookByGetBookReq(GetBooksRequest getBooksRequest);
}
