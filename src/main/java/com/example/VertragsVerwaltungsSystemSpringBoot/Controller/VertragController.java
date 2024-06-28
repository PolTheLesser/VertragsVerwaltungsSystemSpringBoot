package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Die VertragController-Klasse stellt REST-API-Endpunkte zur Verfügung,
 * um auf einzelne Vertragsressourcen zuzugreifen und CRUD-Operationen durchzuführen.
 */
@RestController
@RequestMapping("/vertrag")
public class VertragController {

    // Injektion des VertragsService, der Geschäftslogik und Datenzugriff bereitstellt.
    @Autowired
    private VertragsService vertragsService;

    /**
     * Diese Methode behandelt GET-Anfragen an /vertrag/{vsnr} und gibt den Vertrag
     * mit der angegebenen Versicherungsnummer (VSNR) zurück.
     *
     * @param vsnr die Versicherungsnummer des Vertrags
     * @return eine ResponseEntity mit dem gefundenen Vertrag und dem Status 200 (OK)
     */
    @GetMapping(value = "/{vsnr}")
    public ResponseEntity<Vertrag> getVertragFromVsnr(@PathVariable("vsnr") String vsnr) {
        // Ruft den Vertrag mit der angegebenen Versicherungsnummer vom VertragsService ab und gibt ihn in als ResponseEntity zurück.
        return ResponseEntity.ok(vertragsService.findVertragWithVsnr(vsnr));
    }

    /**
     * Diese Methode behandelt POST-Anfragen an /vertrag/new und erstellt einen neuen Vertrag
     * basierend auf den bereitgestellten Daten.
     *
     * @param vertrag das Vertrag-Objekt, das erstellt werden soll
     * @return eine ResponseEntity mit dem erstellten Vertrag und dem Status 200 (OK)
     */
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertragAnlegen(@RequestBody Vertrag vertrag) {
        // Erstellt einen neuen Vertrag mit den bereitgestellten Daten und gibt ihn in als ResponseEntity zurück.
        return ResponseEntity.ok(vertragsService.neuVertragAnlegen(vertrag));
    }

    /**
     * Diese Methode behandelt PUT-Anfragen an /vertrag/change und aktualisiert einen bestehenden Vertrag
     * basierend auf den bereitgestellten Daten.
     *
     * @param vertrag das Vertrag-Objekt, das aktualisiert werden soll
     * @return eine ResponseEntity mit dem aktualisierten Vertrag und dem Status 200 (OK)
     */
    @PutMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> putAenderungVertrag(@RequestBody Vertrag vertrag) {
        // Aktualisiert einen bestehenden Vertrag mit den bereitgestellten Daten und gibt ihn in als ResponseEntity zurück.
        return ResponseEntity.ok(vertragsService.vertragsAenderung(vertrag));
    }

    /**
     * Diese Methode behandelt DELETE-Anfragen an /vertrag/delete/{vsnr} und löscht den Vertrag
     * mit der angegebenen Versicherungsnummer (VSNR).
     *
     * @param vsnr die Versicherungsnummer des Vertrags, der gelöscht werden soll
     * @return eine ResponseEntity mit einer Bestätigungsmeldung und dem Status 200 (OK)
     */
    @DeleteMapping(value = "/delete/{vsnr}")
    public ResponseEntity<String> deleteVertragVsnr(@PathVariable String vsnr) {
        // Löscht den Vertrag mit der angegebenen Versicherungsnummer und gibt eine Bestätigungsmeldung in als ResponseEntity zurück.
        return ResponseEntity.ok(vertragsService.deleteVertragByVsnr(vsnr));
    }
}