package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.common.Constant;
import com.bkash.bookmanagement.entity.*;
import com.bkash.bookmanagement.repository.*;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

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
        if (! partialName.isEmpty()) {
            var newStr = "%" + partialName.get() + "%";
            partialName = Optional.of(newStr);
        }
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
        return books;
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
    public String addBook(
            Book book,
            List<Integer> auhtorIdList,
            List<Integer> genreIdList
    ) {
        String bookGenreExistError = authorGenreExistError(auhtorIdList, genreIdList);
        if (!bookGenreExistError.equals("")) {
            return bookGenreExistError;
        }
//        TODO : check whether book created time is saved properly

        book.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        bookRepository.save(book);

        bookAuthorGenreSave(book, auhtorIdList, genreIdList);
        return "";
    }

    public void saveOnlyBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findByNameLike(
            String likePattern,
            Integer offset,
            Integer limit
    ) {
        likePattern = likePattern + "%";
        return bookRepository.findBookByNameLikeOrderById(likePattern);
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

//    @Override
//    public List<Book> getBookByGetBookReq(GetBooksRequest getBooksRequest) {
//        List<Book> bookList = bookRepository.findBookByNameLikeOrderById(getBooksRequest.getPrefixName());
//
//        List<BookAuthor> bookAuthorList = bookAuthorRepository.findBookAuthorByAuthorId(getBooksRequest.getAuthorId());
//        Set<Integer> bookIdSetFromAuthor = new HashSet<Integer>();
//        for (BookAuthor bookAuthor : bookAuthorList) {
//            bookIdSetFromAuthor.add(bookAuthor.getBookId());
//        }
//        bookList = getIntersenction(bookList, bookIdSetFromAuthor);
//
//        List<BookGenre> bookGenreList = bookGenreRepository.findBookGenreByGenreId(getBooksRequest.getGenreId());
//        Set<Integer> bookIdSetFromGenre = new HashSet<Integer>();
//        for (BookGenre bookGenre : bookGenreList) {
//            bookIdSetFromGenre.add(bookGenre.getBookId());
//        }
//        bookList = getIntersenction(bookList, bookIdSetFromGenre);
//
//        bookList = bookList.subList(
//                getBooksRequest.getOffset(),
//                getBooksRequest.getLimit()
//        );
//        return bookList;
//    }

//    @Override
//    public List<Book> findByNameLike(
//            String likePattern
////            Integer offset,
////            Integer limit
//    ) {
//        int page = offset / limit;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        return bookRepository.findByNameLike(likePattern + "%", new PageRequest(offset, limit, null));
//    }

    private String authorGenreExistError(
            List<Integer> authorIdList,
            List<Integer> genreIdList
    ) {
        for (Integer authorId : authorIdList) {
            if (!authorRepository.existsById(authorId)) {
                return "authorId = " + String.valueOf(authorId) + " not found ";
            }
        }

        for (Integer genreId : genreIdList) {
            if (!genreRepository.existsById(genreId)) {
                return "genreId " + String.valueOf(genreId) + "not found";
            }
        }

        return "";
    }

    public void bookAuthorGenreSave(
            Book book,
            List<Integer> authorIdList,
            List<Integer> genreIdList
    ) {
        Integer bookId = book.getId();

        // TODO : check whether duplicate entry throws any error
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
