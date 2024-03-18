package com.bkash.bookmanagement.controller;


import com.bkash.bookmanagement.dto.GetBooksRequest;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books_info")
public class BookInfoController {
    private final BookService bookService;

    public BookInfoController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseBody
    public List<Book> getBookInfo(@RequestBody GetBooksRequest getBooksRequest) {
        List<Book> books = bookService.getBookByGetBookReq(getBooksRequest);
        return books;
    }
}
