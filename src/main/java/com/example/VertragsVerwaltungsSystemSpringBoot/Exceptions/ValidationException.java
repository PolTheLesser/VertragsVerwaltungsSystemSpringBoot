package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

/**
 * Die ValidationException-Klasse ist eine benutzerdefinierte Exception,
 * die ausgelöst wird, wenn ein Validierungsfehler auftritt.
 */
public class ValidationException extends RuntimeException {

    /**
     * Konstruktor für ValidationException, der eine Fehlermeldung entgegennimmt.
     *
     * @param message die Fehlermeldung, die die Ursache der Ausnahme beschreibt
     */
    public ValidationException(String message) {
        super(message);
    }
}