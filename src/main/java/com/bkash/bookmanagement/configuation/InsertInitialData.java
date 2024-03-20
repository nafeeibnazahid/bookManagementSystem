package com.bkash.bookmanagement.configuation;

import com.bkash.bookmanagement.dto.auth.UserRequest;
import com.bkash.bookmanagement.repository.auth.UserRepository;
import com.bkash.bookmanagement.services.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InsertInitialData implements CommandLineRunner {

    private final UserService userService;

    public InsertInitialData(UserService userService) {
        this.userService = userService;
    }



    @Override
    public void run(String... args) throws Exception {
        userService.saveUser(new UserRequest(
                null,
                "superadmin",
                "test1234",
                null
        ));
    }

}
