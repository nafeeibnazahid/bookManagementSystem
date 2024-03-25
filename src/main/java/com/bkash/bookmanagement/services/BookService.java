package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.dto.AddBookRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<Book> getBooks(
            Optional<Integer> id,
            Optional<Date> startTime,
            Optional<Date> endTime,
            Optional<String> partialName,
            Optional<Integer> authorId,
            Optional<Integer> genreId,
            Integer offset,
            Integer limit
    );

    public Book getAsingleBook(int bookId);

    public Author addBookAuthor(int bookId, int authorId);

    public void deleteBookAuthor(int bookId, int authorId);

    public Genre addBookGenre(int bookId, int genreId);

    public void removeBookGenre(int bookId, int genreId);

    public Book addBookRequest(
            AddBookRequest addBookRequest
    );

    public void saveOnlyBook(Book book);

    public void bookAuthorGenreSave(
            Book book,
            List<Integer> authorIdList,
            List<Integer> genreIdList
    );

    public Book updateBook(Book book);

    public void deleteBook(Integer bookId);
}
