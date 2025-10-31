package com.example.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Avvio del progetto: una sola classe, un solo main.
 * Spring Boot fa tutto il resto per noi.
 */
@SpringBootApplication
public class TodolistApplication {

    /**
     * Lancia l'applicazione web.
     */
    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }
}
