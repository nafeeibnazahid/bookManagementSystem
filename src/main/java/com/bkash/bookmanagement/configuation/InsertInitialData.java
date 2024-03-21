package com.bkash.bookmanagement.configuation;

import com.bkash.bookmanagement.dto.auth.UserRequest;
import com.bkash.bookmanagement.entity.Genre;
import com.bkash.bookmanagement.repository.auth.UserRepository;
import com.bkash.bookmanagement.services.GenreService;
import com.bkash.bookmanagement.services.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InsertInitialData implements CommandLineRunner {

    private final UserService userService;

    private final GenreService genreService;

    public InsertInitialData(
            UserService userService,
            GenreService genreService
    ) {
        this.userService = userService;
        this.genreService = genreService;
    }


    @Override
    public void run(String... args) throws Exception {
        saveSuperAdmin();
        createGenre();
    }


    private void saveSuperAdmin() {
        userService.saveUser(new UserRequest(
                null,
                "superadmin",
                "test1234",
                null
        ));
    }

    private void createGenre() {
        for (int i = 0; i < 100; i++) {
            Genre genre = new Genre();
            genre.setName("genre" + i);
            genreService.addGenre(genre);
        }
    }
}
