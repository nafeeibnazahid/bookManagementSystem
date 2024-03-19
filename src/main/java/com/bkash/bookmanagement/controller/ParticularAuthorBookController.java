package com.bkash.bookmanagement.controller;

import com.bkash.bookmanagement.dto.UpdateBookIdForASpecificAuthor;
import com.bkash.bookmanagement.services.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("api/v1/author/{authorId}/books")
public class ParticularAuthorBookController {
    private final AuthorService authorService;


    public ParticularAuthorBookController(AuthorService authorService) {
        this.authorService = authorService;
    }

//    @GetMapping
//    public List<Integer> getParticularAuthorBookId(@PathVariable("authorId") Integer authorId) {
//        return authorService.getBookIdListFromAuthorId(authorId);
//    }




    @PutMapping
    public void updateParticularAuthorBookId(@PathVariable("authorId") Integer authorId, @RequestBody
                                             UpdateBookIdForASpecificAuthor updateBookIdForASpecificAuthor) {

    }
}
