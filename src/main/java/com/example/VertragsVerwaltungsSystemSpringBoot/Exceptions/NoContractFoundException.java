package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

public class NoContractFoundException extends RuntimeException {

    public NoContractFoundException(String message) {
        super(message);
    }
}
