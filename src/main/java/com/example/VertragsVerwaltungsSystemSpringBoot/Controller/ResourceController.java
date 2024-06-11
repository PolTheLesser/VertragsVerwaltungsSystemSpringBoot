package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource/{id}")
    public Resource getResource(@PathVariable String id) {
        // gibt html dokument je nach fehler zurück (ist das sinnvoll?)
        /*
        if id == NoContractFound
        return NoContractFound.html

        else if id == URLNotFound
        return URLNotFound.html
         */
    }
}