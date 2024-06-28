package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Die VertraegeController-Klasse stellt REST-API-Endpunkte zur Verfügung,
 * um auf Vertragsressourcen zuzugreifen. Sie verarbeitet HTTP-Anfragen und gibt
 * entsprechende Antworten zurück.
 */
@RestController
@RequestMapping("/vertraege")
public class VertraegeController {

    // Injektion des VertragsService, der Geschäftslogik und Datenzugriff bereitstellt.
    @Autowired
    private VertragsService vertragsService;

    /**
     * Diese Methode behandelt GET-Anfragen an /vertraege/all und gibt eine Liste
     * aller Verträge zurück.
     *
     * @return eine ResponseEntity mit der Liste der Verträge und dem Status 200 (OK)
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<Vertrag>> getVertraege() {
        // Ruft die Liste der Verträge vom VertragsService ab und gibt sie in als ResponseEntity zurück.
        return ResponseEntity.ok(vertragsService.findVertraege());
    }
}