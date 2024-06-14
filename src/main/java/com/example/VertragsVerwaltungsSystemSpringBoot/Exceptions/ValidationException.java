package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
