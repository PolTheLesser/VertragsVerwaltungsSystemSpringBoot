package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Exceptions.ContractNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String URLNotFound(NoHandlerFoundException e, Model model) {
        model.addAttribute("error", "URL not found");
        model.addAttribute("message", e.getMessage());
        return "error/URLNotFound"; // Das wäre die URL der Fehlerseite (ergibt das Sinn?)
    }

    @ExceptionHandler(ContractNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NoContractFound(ContractNotFoundException e, Model model) {
        model.addAttribute("error", "Contract not found");
        model.addAttribute("message", e.getMessage());
        return "error/NoContractFound"; // Das wäre die URL der Fehlerseite (ergibt das Sinn?)
    }
}
