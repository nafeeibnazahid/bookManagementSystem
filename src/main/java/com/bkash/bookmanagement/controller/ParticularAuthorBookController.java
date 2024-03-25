//package com.bkash.bookmanagement.controller;
//
//import com.bkash.bookmanagement.dto.AddBookIdForASpecificAuthor;
//import com.bkash.bookmanagement.services.AuthorService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Component
//@RestController
//@RequestMapping("api/v1/author/{authorId}/books")
//public class ParticularAuthorBookController {
//    private final AuthorService authorService;
//
//
//    public ParticularAuthorBookController(AuthorService authorService) {
//        this.authorService = authorService;
//    }
//
//    @GetMapping
//    public List<Integer> getParticularAuthorBookId(@PathVariable("authorId") Integer authorId) {
//        return authorService.getBookIdListFromAuthorId(authorId);
//    }
//
//    @PostMapping
//    @ResponseBody
//    public void addBookId(
//            @PathVariable("authorId") Integer authorId,
//            @RequestBody
//            AddBookIdForASpecificAuthor addBookIdForASpecificAuthor
//    ) {
//        // Check Book id exist or not
//        // Check author id exist or not
//        // Check whether book_id, author_id already in DB or not
//        // Add it in the DB
//    }
//}
