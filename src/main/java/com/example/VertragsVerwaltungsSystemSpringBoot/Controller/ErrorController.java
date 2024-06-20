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

@ControllerAdvice
public class ErrorController {


    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> urlNotFound(NoResourceFoundException e) {
        return new ResponseEntity<>("Die URL: /" + e.getResourcePath() + " konnte nicht gefunden werden.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>("Die Methode: " + e.getMethod() + " konnte nicht vom Server ausgeführt werden.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoContractFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> noContractFound(NoContractFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<String> wrongInput(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
