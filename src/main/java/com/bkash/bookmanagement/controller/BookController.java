package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.AddBookRequest;
import com.bkash.bookmanagement.dto.auth.AddAuthorToABookRequest;
import com.bkash.bookmanagement.dto.auth.AddGenreToABookRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.services.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}/author")
    @ResponseBody
    public Author addAuthor(
            @PathVariable("bookId") @NotNull Integer bookId,
            @Valid @RequestBody AddAuthorToABookRequest addAuthorToABookRequest
    ) {
        var authorId = addAuthorToABookRequest.authorId;
        return bookService.addBookAuthor(bookId, authorId);
    }

    @DeleteMapping("/{bookId}/author/{authorId}")
    @ResponseBody
    public void deleteAuthor(
            @PathVariable("bookId") @NotNull Integer bookId,
            @PathVariable("authorId") @NotNull Integer authorId
    ) {
        bookService.deleteBookAuthor(bookId, authorId);
    }

    @PostMapping("/{bookId}/genre")
    @ResponseBody
    public Genre addGenre(
            @PathVariable("bookId") @NotNull Integer bookId,
            @Valid @RequestBody AddGenreToABookRequest addGenreToABookRequest
    ) {
        var genreId = addGenreToABookRequest.genreId;
        return bookService.addBookGenre(bookId, genreId);
    }

    @DeleteMapping("/{bookId}/genre/{genreId}")
    @ResponseBody
    public void deleteGenre(
            @PathVariable("bookId") @NotNull Integer bookId,
            @PathVariable("genreId") Integer genreId
    ) {
        bookService.removeBookGenre(bookId, genreId);
    }


    @PostMapping
    @ResponseBody
    public Book addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        if (addBookRequest.authorIdList == null) {
            addBookRequest.authorIdList = new ArrayList<>();
        }
        if (addBookRequest.genreIdList == null) {
            addBookRequest.genreIdList = new ArrayList<>();
        }

        return bookService.addBook(
                addBookRequest.getBook(),
                addBookRequest.getAuthorIdList(),
                addBookRequest.getGenreIdList()
        );
    }

    @GetMapping
    @ResponseBody
    public List<Book> getBooks(
            @RequestParam Optional<Integer> id,
            @RequestParam Optional<String> partialName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<Date> startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<Date> endTime,
            @RequestParam Optional<Integer> authorId,
            @RequestParam Optional<Integer> genreId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return bookService.getBooks(
                id,
                startTime,
                endTime,
                partialName,
                authorId,
                genreId,
                offset,
                limit
        );
    }
}
