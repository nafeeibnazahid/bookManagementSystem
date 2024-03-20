package com.bkash.bookmanagement.applicationStartup;

import com.bkash.bookmanagement.controller.UserController;
import com.bkash.bookmanagement.repository.auth.UserRepository;
import com.bkash.bookmanagement.services.auth.UserService;
import com.bkash.bookmanagement.services.auth.UserServiceImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        System.out.println("before any api call");

        return;
    }
}