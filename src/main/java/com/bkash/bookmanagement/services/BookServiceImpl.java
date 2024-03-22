package com.bkash.bookmanagement.services;

import com.bkash.bookmanagement.dto.GetBooksRequest;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.BookAuthor;
import com.bkash.bookmanagement.entity.BookGenre;
import com.bkash.bookmanagement.repository.*;
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
        return bookRepository.getBooks(
                id,
                startTime,
                endTime,
                partialName,
                authorId,
                offset,
                limit
        );
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

    @Override
    public List<Book> getBookByGetBookReq(GetBooksRequest getBooksRequest) {
        List<Book> bookList = bookRepository.findBookByNameLikeOrderById(getBooksRequest.getPrefixName());

        List<BookAuthor> bookAuthorList = bookAuthorRepository.findBookAuthorByAuthorId(getBooksRequest.getAuthorId());
        Set<Integer> bookIdSetFromAuthor = new HashSet<Integer>();
        for (BookAuthor bookAuthor : bookAuthorList) {
            bookIdSetFromAuthor.add(bookAuthor.getBookId());
        }
        bookList = getIntersenction(bookList, bookIdSetFromAuthor);

        List<BookGenre> bookGenreList = bookGenreRepository.findBookGenreByGenreId(getBooksRequest.getGenreId());
        Set<Integer> bookIdSetFromGenre = new HashSet<Integer>();
        for (BookGenre bookGenre : bookGenreList) {
            bookIdSetFromGenre.add(bookGenre.getBookId());
        }
        bookList = getIntersenction(bookList, bookIdSetFromGenre);

        bookList = bookList.subList(
                getBooksRequest.getOffset(),
                getBooksRequest.getLimit()
        );
        return bookList;
    }

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
