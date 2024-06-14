package com.example.VertragsVerwaltungsSystemSpringBoot.Controller;

import com.example.VertragsVerwaltungsSystemSpringBoot.Model.Vertrag;
import com.example.VertragsVerwaltungsSystemSpringBoot.Services.VertragsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vertrag")
public class KundenController {

    @Autowired
    private VertragsService vertragsService;

    @GetMapping("/all")
    public ResponseEntity<List<Vertrag>> getVertraege() {
        return ResponseEntity.ok(vertragsService.getVertraege());
    }

    @GetMapping("/{vsnr}")
    public ResponseEntity<Vertrag> getVsnrVertrag(@PathVariable("vsnr") String vsnr) {
        return ResponseEntity.ok(vertragsService.getVertrag(vsnr));
    }

    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> postNeuVertrag(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.postNeu(vertrag));
    }

    @PutMapping(value = "/change",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vertrag> putAenderungVertrag(@RequestBody Vertrag vertrag) {
        return ResponseEntity.ok(vertragsService.postAenderung(vertrag));
    }

    @DeleteMapping(value = "/delete/{vsnr}")
    public ResponseEntity<String> deleteVertrag(@PathVariable String vsnr) {
        return ResponseEntity.ok(vertragsService.deleteVertraegeVSNR(vsnr));
    }
}
