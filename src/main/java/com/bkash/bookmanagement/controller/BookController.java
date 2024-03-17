package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.AddBookRequest;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Book> getBooks() {
        return bookService.getBooks();
    }
}
