package com.bkash.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;




@EnableCaching
@SpringBootApplication
//@EnableSwagger2
public class BookManagementSystem {

    //    TODO : Add Swagger or springdoc-openapi-starter-webmvc-ui . Swagger not working properly
    public static void main(String[] args) {
        SpringApplication.run(BookManagementSystem.class, args);
    }
}
