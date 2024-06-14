package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import io.micrometer.core.instrument.config.validate.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {


    @GetMapping("/resource/{id}")
    public ResponseEntity<String> getResource(@PathVariable String id) throws ValidationException {
        return ResponseEntity.ok(id);
    }
}

    /*
    @GetMapping("/resource/{id}")
    public  getResource(@PathVariable String id) {
        // gibt html dokument je nach fehler zurück

        if id == noContractFound
        return NoContractFound.html

        else if id == URLNotFound
        return urlnlotFound.html

        else if id == wrongInput
        return wronginput.html

        return "NoContractFound";
    }
}*/