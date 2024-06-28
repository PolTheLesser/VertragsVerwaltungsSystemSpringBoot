package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.NoContractFoundException;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Die ErrorController-Klasse ist eine globale Fehlerbehandlungs-Klasse,
 * die verschiedene spezifische Ausnahmen abfängt und entsprechende HTTP-Statuscodes
 * sowie Fehlermeldungen zurückgibt. Sie sorgt dafür, dass die Anwendung
 * konsistente und benutzerfreundliche Fehlermeldungen liefert, wenn bestimmte
 * Fehler auftreten.
 */
@ControllerAdvice
public class ErrorController {


    /**
     * Diese Methode behandelt Exceptions vom Typ NoResourceFoundException.
     * Wenn eine solche Ausnahme auftritt, wird ein HTTP-Status 404 (NOT FOUND) zurückgegeben.
     *
     * @param e die ausgelöste NoResourceFoundException
     * @return eine ResponseEntity mit einer Fehlermeldung und dem Status 404
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> urlNotFound(NoResourceFoundException e) {
        // Erstellt eine ResponseEntity mit einer Fehlermeldung, die das Attribut enthält, bei dem der Fehler geworfen wurde.
        return new ResponseEntity<>("Die URL: /" + e.getResourcePath() + " konnte nicht gefunden werden.", HttpStatus.NOT_FOUND);
    }

    /**
     * Diese Methode behandelt die HttpRequestMethodNotSupportedException.
     * Wenn eine solche Ausnahme auftritt, wird ein HTTP-Status 405 (METHOD NOT ALLOWED) zurückgegeben.
     *
     * @param e die ausgelöste HttpRequestMethodNotSupportedException
     * @return eine ResponseEntity mit einer Fehlermeldung und dem Status 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        // Erstellt eine ResponseEntity mit einer Fehlermeldung, die die nicht unterstützte HTTP-Anfrage Enthält.
        return new ResponseEntity<>("Die Methode: " + e.getMethod() + " konnte nicht vom Server ausgeführt werden.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Diese Methode behandelt die NoContractFoundException.
     * Wenn eine solche Exception auftritt, wird ein HTTP-Status 404 (NOT FOUND) zurückgegeben.
     *
     * @param e die ausgelöste NoContractFoundException
     * @return eine ResponseEntity mit der Fehlermeldung der Ausnahme und dem Status 404
     */
    @ExceptionHandler(NoContractFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> noContractFound(NoContractFoundException e) {
        // Erstellt eine ResponseEntity mit der Fehlermeldung der ausgelösten Exception.
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Diese Methode behandelt die ValidationException.
     * Wenn eine solche Exception auftritt, wird ein HTTP-Status 400 (BAD REQUEST) zurückgegeben.
     *
     * @param e die ausgelöste ValidationException
     * @return eine ResponseEntity mit der Fehlermeldung der Ausnahme und dem Status 400
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> wrongInput(ValidationException e) {
        // Erstellt eine ResponseEntity mit der Fehlermeldung der ausgelösten Exception.
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
