package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface AuthorService {


    public Author addAuthor(
            Author author
    );

    public List<Author> getAuthors(
            Optional<Integer> id,
            Optional<String> name,
            Optional<Integer> bookId,
            Integer offset,
            Integer limit
    );

    public Author getSingleAuthor(Integer id);

//    public void deleteById(Integer id);

    public List<Integer> getBookIdListFromAuthorId(Integer authorId);

    public Author updateAuthor(Author author);

    public void deleteAuthor(Integer authorId);
}
