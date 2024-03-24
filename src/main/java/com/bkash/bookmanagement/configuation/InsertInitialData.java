package com.bkash.bookmanagement.configuation;

import com.bkash.bookmanagement.dto.auth.UserRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.services.AuthorService;
import com.bkash.bookmanagement.services.BookService;
import com.bkash.bookmanagement.services.GenreService;
import com.bkash.bookmanagement.services.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

@Component
@Slf4j
public class InsertInitialData implements CommandLineRunner {

    private final UserService userService;

    private final GenreService genreService;

    private final AuthorService authorService;

    private final BookService bookService;


    private final Integer entityStart = 1;
    private final Integer entityEnd = 10;


    public InsertInitialData(
            UserService userService,
            GenreService genreService,
            AuthorService authorService,
            BookService bookService
    ) {
        this.userService = userService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        saveSuperAdmin();
        createEntities();
    }

    private void createEntities() {
        createGenres();
        createAuthors();
        createBooks();
    }


    private void saveSuperAdmin() {
        userService.saveUser(new UserRequest(
                null,
                "superadmin",
                "test1234",
                null
        ));
    }

    private void createGenres() {
        for (int i = entityStart; i < entityEnd; i++) {
            Genre genre = new Genre();
            genre.setName("genre" + i);
            genreService.addGenre(genre);
        }
    }

    public void createAuthors() {
        for (int i = entityStart; i < entityEnd; i++) {
            Author author = new Author();
            author.setName("author" + i);
            authorService.addAuthor(author);
        }
    }

    private ArrayList<Integer> getAuthorGenreList(int i) {
        ArrayList<Integer> arList = new ArrayList<>();
        if (i > entityStart) {
            arList.add(i-1);
        }
        arList.add(i);
        if (i < entityEnd-1) {
            arList.add(i+1);
        }
        return arList;
    }

    public void createBooks() {
        Calendar calendar = Calendar.getInstance();
        long oneDayMilliSeconds = 24 * 60 * 60 * 1000;
        for (int i = entityStart; i < entityEnd; i++) {
            Book book = new Book();
            book.setName("book" + i);
            book.setCreatedAt(new Timestamp(System.currentTimeMillis() - i * oneDayMilliSeconds));
            bookService.saveOnlyBook(book);

            var auhtorGenreIdList = getAuthorGenreList(i);
            bookService.bookAuthorGenreSave(
                book,
                auhtorGenreIdList,
                auhtorGenreIdList
            );
        }
    }
}
