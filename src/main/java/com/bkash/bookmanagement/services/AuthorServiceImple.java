package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.BookAuthor;
import com.bkash.bookmanagement.repository.AuthorRepository;
import com.bkash.bookmanagement.repository.BookAuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public List<Author> getAuthors(
            Optional<Integer> id,
            Optional<String> name,
            Optional<Integer> bookId,
            Integer offset,
            Integer limit
    ) {
        return authorRepository.getAuthor(
                id,
                name,
                bookId,
                offset,
                limit
        );
//        int pageNum = offset / limit;
//        return authorRepository.findAllByOrderByIdDesc(PageRequest.of(pageNum, limit));
    }

    @Override
    public Optional<Author> getSingleAuthor(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }


    public List<Integer> getBookIdListFromAuthorId(Integer authorId) {
        List<BookAuthor> bookAuthorList = bookAuthorRepository.findBookAuthorByAuthorId(authorId);
        return bookAuthorList.stream().map(ob -> ob.getBookId()).toList();
    }
}
