package com.examly.springapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringappApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringappApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringappApplication.class, args);
        logger.info("==========================================================");
        logger.info(" E-Learning Platform Backend Service started successfully ");
        logger.info(" Running on Port: 8080 (Java 17 / Spring Boot)          ");
        logger.info("==========================================================");
    }
}
