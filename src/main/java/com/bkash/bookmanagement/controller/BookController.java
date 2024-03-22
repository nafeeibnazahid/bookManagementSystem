package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.AddBookRequest;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.services.BookService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseBody
    public String addBook(@RequestBody AddBookRequest addBookRequest) {
        String err = bookService.addBook(
                addBookRequest.getBook(),
                addBookRequest.getAuthorIdList(),
                addBookRequest.getGenreIdList()
        );
        return err;
    }

    @GetMapping
    @ResponseBody
    public List<Book> getBooks(
            @RequestParam Optional<Integer> id,
            @RequestParam Optional<String> partialName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<Date> startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<Date> endTime,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return bookService.getBooks(
                id,
                startTime,
                endTime,
                partialName,
                offset,
                limit
        );
    }
}
