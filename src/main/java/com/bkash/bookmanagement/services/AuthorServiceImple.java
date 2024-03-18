package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.repository.AuthorRepository;
import com.bkash.bookmanagement.repository.BookAuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorServiceImple implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    public AuthorServiceImple(
            AuthorRepository authorRepository,
            BookAuthorRepository bookAuthorRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public void addAuthor(Author author) {
//        TODO : check whether duplicate author_name there
        authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthors(Integer offset, Integer limit) {
        int pageNum = offset / limit;
        return authorRepository.findAllByOrderByIdDesc(PageRequest.of(pageNum, limit));
    }
}
