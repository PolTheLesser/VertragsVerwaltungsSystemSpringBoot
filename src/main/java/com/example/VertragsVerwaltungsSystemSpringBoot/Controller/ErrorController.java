package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.NoContractFoundException;
import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @Autowired
    ResourceController resourceController;

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> urlNotFound(NoHandlerFoundException e) {
        return resourceController.getResource(e.getMessage());
    }

    @ExceptionHandler(NoContractFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> noContractFound(NoContractFoundException e) {
        return resourceController.getResource(e.getMessage()); // Das wäre die URL der Fehlerseite (ergibt das Sinn?)
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> wrongInput(ValidationException e) {
        return resourceController.getResource(e.getMessage()); // so muss ich kein Spring MVC nutzen (Ist das trotzdem okay?)
    }
}
