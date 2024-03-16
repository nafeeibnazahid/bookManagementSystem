package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorServiceImple implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImple(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void addAuthor(Author author) {
//        TODO : check whether duplicate author_name there
        authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }
}
