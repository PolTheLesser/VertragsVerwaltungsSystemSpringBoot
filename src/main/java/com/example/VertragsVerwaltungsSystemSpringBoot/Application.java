package com.example.VertragsVerwaltungsSystemSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // Hauptmethode, die beim Start der Anwendung aufgerufen wird
    public static void main(String[] args) {
        // SpringApplication.run startet die Spring-Anwendung
        SpringApplication.run(Application.class, args);
    }

}