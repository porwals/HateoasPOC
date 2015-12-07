package com.poc.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@EnableEntityLinks
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
