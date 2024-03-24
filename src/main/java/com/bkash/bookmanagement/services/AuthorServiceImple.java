package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.BookAuthor;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.repository.AuthorRepository;
import com.bkash.bookmanagement.repository.BookAuthorRepository;
import com.bkash.bookmanagement.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorServiceImple implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    private final BookRepository bookRepository;

    public AuthorServiceImple(
            AuthorRepository authorRepository,
            BookAuthorRepository bookAuthorRepository,
            BookRepository bookRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthors(
            Optional<Integer> id,
            Optional<String> name,
            Optional<Integer> bookId,
            Integer offset,
            Integer limit
    ) {
        if (offset == null) {
            offset = 0;
        }
        List<Author> authorList = authorRepository.getAuthor(
                id,
                name,
                bookId,
                offset,
                limit
        );
        for (Author author : authorList) {
            author.setBookList(
                    bookRepository.getBooks(
                            null,
                            null,
                            null,
                            null,
                            Optional.of(author.getId()),
                            null,
                            Constant.OFFSET_ZERO,
                            Constant.INFINITE_LIMIT
                    )
            );
        }
        return authorList;
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

    private void validateForUpdateAuthor(
            Author oldAuthor,
            Author newAuthor
    ) {
        if (oldAuthor.equals(newAuthor)) {
            throw new RuntimeException("same as previously saved author");
        }
        Author alreadyInDbWithSameName = authorRepository.findByName(newAuthor.getName());
        if (alreadyInDbWithSameName != null && alreadyInDbWithSameName.getId() != newAuthor.getId()) {
            throw new RuntimeException("same name in DB with id " + alreadyInDbWithSameName.getId());
        }
    }


    @Override
    public Author updateAuthor(Author newAuthor) {
        Author oldAuthor = authorRepository.getReferenceById(newAuthor.getId());
        validateForUpdateAuthor(oldAuthor, newAuthor);
        oldAuthor.setName(newAuthor.getName());
        return authorRepository.save(oldAuthor);
    }


    @Override
    public void deleteAuthor(Integer authorId){
        List<BookAuthor> bookAuthorList = bookAuthorRepository.findBookAuthorByAuthorId(authorId) ;
        if (bookAuthorList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append( "Author id " + authorId + " exists for books with id ");
            for (var bookAuthor: bookAuthorList) {
                stringBuilder.append( bookAuthor.getBookId() + ", " );
            }
            throw new RuntimeException(stringBuilder.toString());
        }
        if (authorRepository.findById(authorId).isEmpty()) {
            throw new RuntimeException("author is absent for id " + authorId);
        }
        authorRepository.deleteById(authorId);
    }

}
