package com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions;

/**
 * Die ValidationError-Klasse stellt eine Validierungsfehlernachricht dar,
 * die Informationen über ein fehlerhaftes Attribut und eine entsprechende Fehlermeldung enthält.
 */
public class ValidationError {

    // Das Attribut, das den Validierungsfehler verursacht hat.
    private String attribute;
    // Die Fehlermeldung, die den Validierungsfehler beschreibt.
    private String message;

    /**
     * Konstruktor für ValidationError, der das fehlerhafte Attribut und die Fehlermeldung entgegennimmt.
     *
     * @param attribute das Attribut, das den Fehler verursacht hat
     * @param message die Fehlermeldung, die den Fehler beschreibt
     */
    public ValidationError(String attribute, String message) {
        this.attribute = attribute;
        this.message = message;
    }

    /**
     * Gibt das fehlerhafte Attribut zurück.
     *
     * @return das fehlerhafte Attribut
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Gibt die Fehlermeldung zurück, die den Validierungsfehler beschreibt.
     *
     * @return die Fehlermeldung
     */
    public String getMessage() {
        return message;
    }
}