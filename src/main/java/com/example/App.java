package com.example;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {
    static final Logger log = LogManager.getLogger(App.class);
    public static void main( String[] args ) {
        log.info("Application started successfully!");
        SpringApplication.run(App.class, args);
    }
}
