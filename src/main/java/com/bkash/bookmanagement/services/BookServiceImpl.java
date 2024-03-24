package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.*;
import com.bkash.bookmanagement.repository.*;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookGenreRepository bookGenreRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookAuthorRepository bookAuthorRepository,
            BookGenreRepository bookGenreRepository,
            AuthorService authorService,
            GenreService genreService
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }


    @Override
    public List<Book> getBooks(
            Optional<Integer> id,
            Optional<Date> startTime,
            Optional<Date> endTime,
            Optional<String> partialName,
            Optional<Integer> authorId,
            Optional<Integer> genreId,
            Integer offset,
            Integer limit
    ) {
        String newStr = "%%";
        if ( partialName != null ) {
            newStr = "%" + partialName.get() + "%";
        }
        partialName = Optional.of(newStr);
        List<Book> books = bookRepository.getBooks(
                id,
                startTime,
                endTime,
                partialName,
                authorId,
                genreId,
                offset,
                limit
        );
        for (Book book : books) {
            setAuthorGenreList(book);
        }
        return books;
    }

    private void setAuthorGenreList(Book book) {
        book.setAuthorList(authorRepository.getAuthor(
                null,
                null,
                Optional.of(book.getId()),
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        ));
        book.setGenreList(genreRepository.getGenre(
                null,
                null,
                Optional.of(book.getId()),
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        ));
    }


    @Override
    public Author addBookAuthor(
            int bookId,
            int authorId
    ) {
        if (! bookRepository.existsById(bookId)) {
            throw new RuntimeException("book doesn't exist");
        }
        if (!authorRepository.existsById(authorId)) {
            throw new RuntimeException("authro doesn't exist");
        }
        if (bookAuthorRepository.existsByBookIdAndAuthorId(bookId, authorId)) {
            throw new RuntimeException("book author relationship already exist");
        }
        BookAuthor bookAuthor = new BookAuthor(
                bookId,
                authorId
        );
        bookAuthorRepository.save(bookAuthor);
        List<Author> authorList = authorService.getAuthors(
                Optional.of(authorId),
                null,
                null,
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        );
        return authorList.get(0);
    }

    @Override
    public void deleteBookAuthor(
            int bookId,
            int authorId
    ) {
        var bookAuthor = bookAuthorRepository.findOne(
                Example.of(new BookAuthor(
                        bookId,
                        authorId
                ))
        );
        if (bookAuthor.isEmpty()) {
            throw new RuntimeException("Book Author relation doesn't exist");
        }
        bookAuthorRepository.delete(bookAuthor.get());
    }

    @Override
    public Genre addBookGenre(
            int bookId,
            int genreId
    ) {
        if (! bookRepository.existsById(bookId)) {
            throw new RuntimeException("book doesn't exist");
        }
        if (!genreRepository.existsById(genreId)) {
            throw new RuntimeException("authro doesn't exist");
        }
        if (bookGenreRepository.existsByBookIdAndGenreId(bookId, genreId)) {
            throw new RuntimeException("book author relationship already exist");
        }
        BookGenre bookGenre = new BookGenre(
                bookId,
                genreId
        );
        bookGenreRepository.save(bookGenre);

        List<Genre> genreList = genreService.getGenre(
                Optional.of(genreId),
                null,
                null,
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        );
        return genreList.get(0);
    }

    @Override
    public void removeBookGenre(
            int bookId,
            int genreId
    ) {
        var bookGenre = bookGenreRepository.findOne(
                Example.of(new BookGenre(
                        bookId,
                        genreId
                ))
        );
        if (bookGenre.isEmpty()) {
            throw new RuntimeException("Book Genre relation doesn't exist");
        }
        bookGenreRepository.delete(bookGenre.get());
    }

    @Override
    public Book addBook(
            Book book,
            List<Integer> auhtorIdList,
            List<Integer> genreIdList
    ) {
        validateAuthorGenreDoesNotExistError(auhtorIdList, genreIdList);

        book.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        book = bookRepository.save(book);
        var bookId = book.getId();

        bookAuthorGenreSave(book, auhtorIdList, genreIdList);
        book = getBooks(
                Optional.of(bookId),
                null,
                null,
                null,
                null,
                null,
                Constant.OFFSET_ZERO,
                Constant.INFINITE_LIMIT
        ).get(0);
        return book;
    }

    public void saveOnlyBook(Book book) {
        bookRepository.save(book);
    }


    private List<Book> getIntersenction(
            List<Book> bookList,
            Set<Integer> bookIdSet
    ) {
        List<Book> ret = new LinkedList<Book>();
        ret = bookList.stream()
                .filter(b -> bookIdSet.contains(b.getId()))
                .toList();
        return ret;
    }


    private void validateAuthorGenreDoesNotExistError(
            List<Integer> authorIdList,
            List<Integer> genreIdList
    ) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer authorId : authorIdList) {
            if (!authorRepository.existsById(authorId)) {
                stringBuilder.append("authorId = " + String.valueOf(authorId) + " not found ");
            }
        }
        for (Integer genreId : genreIdList) {
            if (!genreRepository.existsById(genreId)) {
                stringBuilder.append("genreId " + String.valueOf(genreId) + "not found");
            }
        }
        if (stringBuilder.length() > 0) {
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public void bookAuthorGenreSave(
            Book book,
            List<Integer> authorIdList,
            List<Integer> genreIdList
    ) {
        Integer bookId = book.getId();

        authorIdList = authorIdList.stream().distinct().collect(Collectors.toList());
        for (Integer authorId : authorIdList) {
            BookAuthor bookAuthor = new BookAuthor(bookId, authorId);
            bookAuthorRepository.save(bookAuthor);
        }

        genreIdList = genreIdList.stream().distinct().collect(Collectors.toList());
        for (Integer genreId : genreIdList) {
            BookGenre bookGenre = new BookGenre(bookId, genreId);
            bookGenreRepository.save(bookGenre);
        }

    }


    private void validateForUpdateBook(
            Book oldBook,
            Book newBook
    ) {
        if (oldBook.equals(newBook)) {
            throw new RuntimeException("same as previously saved book");
        }
        Book alreadyInDbWithSameName = bookRepository.findByName(newBook.getName());
        if (alreadyInDbWithSameName != null && alreadyInDbWithSameName.getId() != newBook.getId()) {
            throw new RuntimeException("same name in DB with id " + alreadyInDbWithSameName.getId());
        }
    }


    @Override
    public Book updateBook(Book newBook) {
        Book oldBook = bookRepository.getReferenceById(newBook.getId());
        validateForUpdateBook(oldBook, newBook);
        oldBook.setName(newBook.getName());
        oldBook = bookRepository.save(oldBook);
        setAuthorGenreList(oldBook);
        return oldBook;
    }

    @Override
    public void deleteBook(Integer bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Book doesn't exist in DB");
        }
        bookRepository.deleteById(bookId);
        bookAuthorRepository.deleteByBookId(bookId);
        bookGenreRepository.deleteByBookId(bookId);
    }


}
