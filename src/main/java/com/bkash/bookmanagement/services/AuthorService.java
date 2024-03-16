package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface AuthorService {


    public void addAuthor(Author author);

    public List<Author> getAuthors();
}
