package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.BookAuthor;
import com.bkash.bookmanagement.entity.BookGenre;
import com.bkash.bookmanagement.repository.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookGenreRepository bookGenreRepository;


    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookAuthorRepository bookAuthorRepository,
            BookGenreRepository bookGenreRepository
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

//    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//        this.genreRepository = genreRepository;
//    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public String addBook(Book book, List<Integer> auhtorIdList, List<Integer> genreIdList) {
        String bookGenreExistError = authorGenreExistError(auhtorIdList, genreIdList);
        if (! bookGenreExistError.equals("")) {
            return bookGenreExistError;
        }

        bookRepository.save(book);

//        TODO : save Book created time

        bookAuthorGenreSave(book, auhtorIdList, genreIdList);
        return "";
    }

    private String authorGenreExistError(List<Integer> authorIdList, List<Integer> genreIdList) {
        for (Integer authorId : authorIdList) {
            if ( ! authorRepository.existsById(authorId) ) {
                return "authorId = " + String.valueOf(authorId) + " not found ";
            }
        }

        for (Integer genreId : genreIdList) {
            if (! genreRepository.existsById(genreId)) {
                return "genreId " + String.valueOf(genreId) + "not found";
            }
        }

        return "";
    }

    private void bookAuthorGenreSave(Book book, List<Integer> authorIdList, List<Integer> genreIdList) {
        Integer bookId = book.getId();

        for (Integer authorId : authorIdList) {
            BookAuthor bookAuthor = new BookAuthor(bookId, authorId);
            bookAuthorRepository.save(bookAuthor);
        }

        for (Integer genreId : genreIdList) {
            BookGenre bookGenre = new BookGenre(bookId, genreId);
            bookGenreRepository.save(bookGenre);
        }

    }

}
