package com.bkash.bookmanagement.configuation;

import com.bkash.bookmanagement.dto.auth.UserRequest;
import com.bkash.bookmanagement.entity.Author;
import com.bkash.bookmanagement.entity.Book;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.services.AuthorService;
import com.bkash.bookmanagement.services.GenreService;
import com.bkash.bookmanagement.services.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Slf4j
public class InsertInitialData implements CommandLineRunner {

    private final UserService userService;

    private final GenreService genreService;

    private final AuthorService authorService;

    public InsertInitialData(
            UserService userService,
            GenreService genreService,
            AuthorService authorService
    ) {
        this.userService = userService;
        this.genreService = genreService;
        this.authorService = authorService;
    }


    @Override
    public void run(String... args) throws Exception {
        saveSuperAdmin();
        createGenres();
        createAuthors();
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
        for (int i = 0; i < 100; i++) {
            Genre genre = new Genre();
            genre.setName("genre" + i);
            genreService.addGenre(genre);
        }
    }

    public void createAuthors() {
        for (int i = 0; i < 100; i++) {
            Author author = new Author();
            author.setName("author" + i);
            authorService.addAuthor(author);
        }
    }

//    public void addBooks() {
////        int timestamp
//        for (int i = 0; i < 100; i++) {
//            Book book = new Book();
//            book.setName("book" + i);
//            book.setCreatedAt(new Timestamp());
//        }
//    }
}
