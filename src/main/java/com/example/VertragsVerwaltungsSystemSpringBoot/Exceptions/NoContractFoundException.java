package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

/**
 * Die NoContractFoundException-Klasse ist eine benutzerdefinierte Exception,
 * die ausgelöst wird, wenn keine Verträge gefunden werden.
 */
public class NoContractFoundException extends RuntimeException {

    /**
     * Konstruktor für NoContractFoundException, der eine Fehlermeldung entgegennimmt.
     *
     * @param message die Fehlermeldung, die die Ursache der Ausnahme beschreibt
     */
    public NoContractFoundException(String message) {
        super(message);
    }
}